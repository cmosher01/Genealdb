package nu.mine.mosher.genealdb;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import nu.mine.mosher.genealdb.model.Sample;
import nu.mine.mosher.genealdb.model.entity.conclude.Sameness;
import nu.mine.mosher.genealdb.model.entity.extract.*;
import nu.mine.mosher.genealdb.model.entity.place.*;
import nu.mine.mosher.genealdb.model.entity.source.Citation;
import nu.mine.mosher.genealdb.model.type.ObjectRef;
import nu.mine.mosher.genealdb.view.*;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.ogm.driver.Driver;
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver;
import org.neo4j.ogm.session.*;
import org.slf4j.*;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Runtime.getRuntime;
import static java.util.stream.Collectors.toList;
import static nu.mine.mosher.genealdb.view.Expandable.expd;
import static nu.mine.mosher.genealdb.view.Line.*;

public class Genealdb {
    private static Logger LOG = LoggerFactory.getLogger(Genealdb.class);

    private static final URI URI_NEO4J = URI.create("bolt://localhost");

    private static final String[] packagesEntity = new String[] {
        Citation.class.getPackage().getName(),
        Place.class.getPackage().getName(),
        Event.class.getPackage().getName(),
        Sameness.class.getPackage().getName()
    };

    public static void main(final String... args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("usage: genealdb c|s");
        }



        LOG.trace("will open connection to Neo4j using Bolt driver...");
        final Driver driverNeo = new BoltDriver(GraphDatabase.driver(URI_NEO4J));
        LOG.trace("will create new session...");
        final SessionFactory factoryNeo = new SessionFactory(driverNeo, packagesEntity);


        if (args[0].equalsIgnoreCase("c")) {
            LOG.trace("received command 'C', will create sample objects in database...");
            try {
                factoryNeo.openSession().save(Sample.buildEntities());
            } finally {
                factoryNeo.close();
                driverNeo.close();
            }
        } else if (args[0].equalsIgnoreCase("s")) {
            LOG.trace("received command 'S', will begin serving objects from database...");
            serve(factoryNeo);
        }

        System.out.flush();
        System.err.flush();
    }

    private static void serve(final SessionFactory factoryNeo) throws IOException {
        final Session neo = factoryNeo.openSession();

        final ObjectRef init = getArbitraryCitation(neo);
        if (Objects.isNull(init)) {
            factoryNeo.close();
            return;
        }

        final STGroupFile stg = new STGroupFile("page.stg");

        final NanoHTTPD server = new NanoHTTPD(8080) {
            @Override
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Response serve(final IHTTPSession http) {
                final ObjectRef objRef = getObjectRef(http, init);

                final Object object = neo.load(objRef.cls(), objRef.id(), 3);
                if (Objects.isNull(object)) {
                    return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "page not found");
                }
                final Expandable view;
                if (objRef.cls().equals(Citation.class)) {
                    final Citation entity = (Citation)object;
                    view = buildView(entity);
                } else if (objRef.cls().equals(Persona.class)) {
                    final Persona entity = (Persona)object;
                    view = buildView(entity);
                } else if (objRef.cls().equals(Place.class)) {
                    final Place entity = (Place)object;
                    view = buildView(entity);
                } else {
                    view = Expandable.expd(blank());
                }

                return newFixedLengthResponse(prePage(stg, view));
            }
        };

        LOG.trace("received command 'C', will create sample objects in database...");
        server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);

        getRuntime().addShutdownHook(new Thread(() -> {
            factoryNeo.close();
            server.stop();
        }));
}

    private static ObjectRef getArbitraryCitation(final Session neo) {
        try {
            final Collection<Citation> citations = neo.loadAll(Citation.class, 1);
            final Citation citDefault = citations.iterator().next();
            return new ObjectRef(Citation.class, citDefault.getId());
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    private static ObjectRef getObjectRef(final IHTTPSession http, final ObjectRef def) {
        final Class cls;
        try {
            final List<String> arg = http.getParameters().get("entity");
            cls = Class.forName(arg.get(0));
        } catch (final Throwable ignore) {
            return def;
        }

        final Long i;
        try {
            final List<String> arg = http.getParameters().get("id");
            i = Long.valueOf(arg.get(0));
        } catch (final Throwable ignore) {
            return def;
        }

        return new ObjectRef(cls,i);
    }

    private static Expandable buildView(final Persona persona) {
        final List<Expandable> re = persona.getRoles().stream()
            .map(Role::getEvent)
            .distinct()
            .sorted()
            .map(Genealdb::getEventDisplay)
            .collect(Collectors.toList());

        final List<Expandable> rx = persona.getXrefs().stream()
            .map(is ->
                expd(line(is.getSameness().getRationale()).withLabel(is.getSameness().getCites()),
                    getXrefDisplay(is.getSameness())))
            .collect(toList());

        return expd(Line.blank(),
                expd(line(persona.getName())),
                expd(line(persona.getCites()).withLabel("source")),
                expd(blank().withLabel("extracted events"), re),
                expd(blank().withLabel("cross references"), rx));
    }

    private static Expandable buildView(final Citation citation) {
        final List<Expandable> re = citation.getPersonae().stream()
            .flatMap(persona -> persona.getRoles().stream())
            .map(Role::getEvent)
            .distinct()
            .sorted()
            .map(Genealdb::getEventDisplay)
            .collect(Collectors.toList());

        final List<Expandable> rx = citation.getMatchings().stream()
            .map(sameness ->
                expd(line(sameness.getRationale()),
                    getXrefDisplay(sameness)))
            .collect(Collectors.toList());

        return expd(Line.blank(),
                expd(line(citation.getBrief()).withLabel("brief")),
                expd(line(citation.getUri()).withLabel("full")),
                expd(blank().withLabel("extracted events"), re),
                expd(blank().withLabel("conclusions"), rx));
    }

    private static Expandable buildView(final Place place) {
        final SortedMap<PlaceChange, List<Expandable>> pc = new TreeMap<>();
        place.getConstruction().forEach(c -> cons(c, pc));
        place.getDestruction().forEach(c -> destr(c, pc));
        place.getSuperiors().forEach(s -> supers(s, pc));
        place.getGains().forEach(s -> gains(s, pc));
        place.getLosses().forEach(s -> losses(s, pc));

        final List<Expandable> pcs = new ArrayList<>();
        pc.forEach((key, value) -> pcs.add(expd(getPlaceChangeDisplay(key).withLabel("during"), value)));

        return expd(blank(),
            expd(line(place.getName())),
            expd(blank().withLabel("transfers and transforms"), pcs));
    }

    private static void cons(Transform c, SortedMap<PlaceChange, List<Expandable>> pc) {
        List<Expandable> others = c.getFrom().stream().map(t -> expd(line(t).withLabel("from"))).collect(toList());
        if (others.isEmpty()) {
            others = new ArrayList<>();
            others.add(expd(line("[created]")));
        }
        pc.computeIfAbsent(c.getDuring(), k -> new ArrayList<>());
        pc.get(c.getDuring()).addAll(others);
    }

    private static void destr(Transform c, SortedMap<PlaceChange, List<Expandable>> pc) {
        List<Expandable> others = c.getTo().stream().map(t -> expd(line(t).withLabel("to"))).collect(toList());
        if (others.isEmpty()) {
            others = new ArrayList<>();
            others.add(expd(line("[destroyed]")));
        }
        pc.computeIfAbsent(c.getDuring(), k -> new ArrayList<>());
        pc.get(c.getDuring()).addAll(others);
    }

    private static void supers(Transfer c, SortedMap<PlaceChange, List<Expandable>> pc) {
        List<Expandable> others = c.getFromSuperior().stream().map(t -> expd(line(t).withLabel("from"))).collect(toList());
        pc.computeIfAbsent(c.getDuring(), k -> new ArrayList<>());
        pc.get(c.getDuring()).addAll(others);

        others = c.getToSuperior().stream().map(t -> expd(line(t).withLabel("to"))).collect(toList());
        pc.computeIfAbsent(c.getDuring(), k -> new ArrayList<>());
        pc.get(c.getDuring()).addAll(others);
    }

    private static void gains(Transfer c, SortedMap<PlaceChange, List<Expandable>> pc) {
        List<Expandable> others = c.getOfInferior().stream().map(t -> expd(line(t).withLabel("gained "))).collect(toList());
        pc.computeIfAbsent(c.getDuring(), k -> new ArrayList<>());
        pc.get(c.getDuring()).addAll(others);
    }

    private static void losses(Transfer c, SortedMap<PlaceChange, List<Expandable>> pc) {
        List<Expandable> others = c.getOfInferior().stream().map(t -> expd(line(t).withLabel("lost "))).collect(toList());
        pc.computeIfAbsent(c.getDuring(), k -> new ArrayList<>());
        pc.get(c.getDuring()).addAll(others);
    }

    private static Line getPlaceChangeDisplay(final PlaceChange pc) {
        return line(pc.getDay().getDisplay(), pc.getNotes());
    }

    private static List<Expandable> getXrefDisplay(final Sameness sameness) {
        return sameness.getAre().stream()
            .map(is -> expd(line(
                "("+is.getCertainty()+")",
                is.getPersona(),
                is.getPersona().getCites(),
                is.getNotes())))
            .collect(toList());
    }

    private static Expandable getEventDisplay(final Event event) {
        return expd(line(event.getDay().getDisplay(), event.getPlace(), event.getType(), event.getDescription()),
            event.getPlayers().stream()
                .map(role -> expd(line(role.getPersona()).withLabel(role.getDisplay())))
                .collect(toList()));
    }

    private static String prePage(final STGroupFile stg, final Expandable x) {
        return stg
            .getInstanceOf("prepage")
            .add("x", x)
            .render();
    }
}
