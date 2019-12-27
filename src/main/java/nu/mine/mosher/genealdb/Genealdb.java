package nu.mine.mosher.genealdb;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import nu.mine.mosher.genealdb.model.Sample;
import nu.mine.mosher.genealdb.model.entity.conclude.Sameness;
import nu.mine.mosher.genealdb.model.entity.extract.*;
import nu.mine.mosher.genealdb.model.entity.place.Place;
import nu.mine.mosher.genealdb.model.entity.source.Citation;
import nu.mine.mosher.genealdb.model.type.ObjectRef;
import nu.mine.mosher.genealdb.view.*;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.ogm.driver.Driver;
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver;
import org.neo4j.ogm.session.*;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Runtime.getRuntime;
import static java.util.stream.Collectors.toList;
import static nu.mine.mosher.genealdb.view.Expandable.expd;
import static nu.mine.mosher.genealdb.view.Line.*;

public class Genealdb {
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



        final Driver driverNeo = new BoltDriver(GraphDatabase.driver("bolt://localhost"));
        final SessionFactory factoryNeo = new SessionFactory(driverNeo, packagesEntity);


        if (args[0].equalsIgnoreCase("c")) {
            try {
                factoryNeo.openSession().save(Sample.buildEntities());
            } finally {
                factoryNeo.close();
                driverNeo.close();
            }
        } else if (args[0].equalsIgnoreCase("s")) {
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
        final List<Expandable> rt = new ArrayList<>();

        return expd(Line.blank(),
            expd(line(place.getName())),
            expd(blank().withLabel("transitions"), rt));
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
