package nu.mine.mosher.genealdb;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import nu.mine.mosher.genealdb.model.Sample;
import nu.mine.mosher.genealdb.model.entity.conclude.Sameness;
import nu.mine.mosher.genealdb.model.entity.extract.*;
import nu.mine.mosher.genealdb.model.entity.place.Place;
import nu.mine.mosher.genealdb.model.entity.source.Citation;
import nu.mine.mosher.genealdb.view.Expandable;
import nu.mine.mosher.genealdb.view.Line;
import org.neo4j.ogm.config.*;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.stringtemplate.v4.STGroupFile;

import static java.lang.Runtime.getRuntime;
import static java.util.stream.Collectors.toList;
import static nu.mine.mosher.genealdb.view.Expandable.expd;
import static nu.mine.mosher.genealdb.view.Line.blank;
import static nu.mine.mosher.genealdb.view.Line.line;

public class Genealdb {
    private static final String[] packagesEntity = new String[] {
        Citation.class.getPackageName(),
        Place.class.getPackageName(),
        Event.class.getPackageName(),
        Sameness.class.getPackageName()
    };

    public static void main(final String... args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("usage: genealdb c|s");
        }



        final SessionFactory factoryNeo = createFactoryNeo();



        if (args[0].equalsIgnoreCase("c")) {
            try {
                factoryNeo.openSession().save(Sample.buildEntities());
            } finally {
                factoryNeo.close();
            }
        } else if (args[0].equalsIgnoreCase("s")) {
            serve(factoryNeo);
        }



        System.out.flush();
        System.err.flush();
    }

    private static SessionFactory createFactoryNeo() {
        final ConfigurationSource configSourceNeo = new ClasspathConfigurationSource("ogm.properties");
        final Configuration configNeo = new Configuration.Builder(configSourceNeo).build();
        return new SessionFactory(configNeo, packagesEntity);
    }

    private static void serve(final SessionFactory factoryNeo) throws IOException {
        final Session neo = factoryNeo.openSession();

        final Collection<Citation> citations = neo.loadAll(Citation.class, 1);
        final Citation citDefault = citations.iterator().next();
        final Long idDefault = citDefault.getId();

        final STGroupFile stg = new STGroupFile("page.stg");

        final NanoHTTPD server = new NanoHTTPD(8080) {
            @Override
            public Response serve(final IHTTPSession http) {
                final Class cls = getClassParam(http, Citation.class);
                final Long id = getIdParam(http, idDefault);

                final Object object = neo.load(cls, id, 6);
                final Expandable view;
                if (cls.equals(Citation.class)) {
                    final Citation entity = (Citation)object;
                    view = buildView(entity);
                } else if (cls.equals(Persona.class)) {
                    final Persona entity = (Persona)object;
                    view = buildView(entity);
                } else if (cls.equals(Place.class)) {
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

    private static Long getIdParam(final IHTTPSession http, final Long idDefault) {
        final List<String> id = http.getParameters().get("id");
        if (id == null || id.isEmpty()) {
            return idDefault;
        }
        return Long.valueOf(id.get(0));
    }

    private static Class getClassParam(final IHTTPSession http, final Class classDefault) {
        final List<String> entity = http.getParameters().get("entity");
        if (entity == null || entity.isEmpty()) {
            return classDefault;
        }
        try {
            return Class.forName(entity.get(0));
        } catch (final ClassNotFoundException ignore) {
            return classDefault;
        }
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

    private static List<Expandable> getXrefDisplay(final Sameness sameness) {
        return sameness.getAre().stream()
            .map(is -> expd(line(
                is.getCertainty(),
                is.getPersona(),
                is.getPersona().getCites(),
                is.getNotes())))
            .collect(toList());
    }

    private static Expandable getEventDisplay(final Event event) {
        return expd(line(event.getDisplay()),
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
