package nu.mine.mosher.genealdb;

import java.io.PrintWriter;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import nu.mine.mosher.genealdb.model.entity.*;
import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.Day;
import nu.mine.mosher.genealdb.view.Expandable;
import nu.mine.mosher.genealdb.view.Line;
import org.neo4j.ogm.config.*;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.stringtemplate.v4.STGroupFile;
import spark.Request;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static nu.mine.mosher.genealdb.model.type.Certainty.MUST;
import static nu.mine.mosher.genealdb.view.Expandable.expd;
import static nu.mine.mosher.genealdb.view.Line.blank;
import static nu.mine.mosher.genealdb.view.Line.line;
import static spark.Spark.get;

public class Genealdb {
    public static void main(final String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("usage: genealdb c|s");
        }

        final ConfigurationSource configSourceDb = new ClasspathConfigurationSource("ogm.properties");
        final Configuration configDb = new Configuration.Builder(configSourceDb).build();
        final SessionFactory sessionFactory = new SessionFactory(configDb, Event.class.getPackageName());
        //try {
            final Session session = sessionFactory.openSession();
            if (args[0].equalsIgnoreCase("c")) {
                create(session);
                sessionFactory.close(); ////////////
            //} else if (args[0].equalsIgnoreCase("r")) {
            //    read(session);
            } else if (args[0].equalsIgnoreCase("s")) {
                serve(session);
                // TODO: how to close sessionFactory?
            }
        //} finally {
        //    sessionFactory.close();
        //}




        //final STGroupFile stg = new STGroupFile("page.stg");
        //
        //final Object foo = new Object() {
        //    public UUID id = UUID.randomUUID();
        //    @Override
        //    public String toString() {
        //        return "click me";
        //    }
        //};
        //
        //final Expandable x =
        //    expd(line("foo"), asList(
        //        expd(line("stuff"), asList(
        //            expd(line("one").withLabel("this")),
        //            expd(line(foo).withLabel("that"))
        //        )),
        //        expd(line(LocalDateTime.now(), "now", new Date())))
        //    );
        //System.out.println(stg.getInstanceOf("prePage").add("x", x).render());

        System.out.flush();
        System.err.flush();
    }

    private static void serve(final Session session) {
        final Collection<Citation> citations = session.loadAll(Citation.class, 1);
        final Citation citDefault = citations.iterator().next();
        final Long idDefault = citDefault.getId();

        final STGroupFile stg = new STGroupFile("page.stg");
        get("/", (req, res) -> getPagePre(stg, session.load(getClass(req, Citation.class), getId(req, idDefault), 4)));
    }

    private static String getPagePre(final STGroupFile stg, final Object entity) {
        final Class c = entity.getClass();
        if (c.equals(Citation.class)) {
            return getCitationPagePre(stg, (Citation)entity);
        } else if (c.equals(Persona.class)) {
            return getPersonaPagePre(stg, (Persona)entity);
        }
        return null;
    }

    private static String getPersonaPagePre(final STGroupFile stg, final Persona persona) {
        final List<Expandable> re = persona.getRoles().stream()
            .map(Role::getEvent)
            .distinct()
            .sorted()
            .map(Genealdb::getEventDisplay)
            .collect(Collectors.toList());

        final List<Expandable> rx = persona.getXrefs().stream()
            .map(is ->
                expd(line(is.getSameness().getRationale()).withLabel(is.getSameness().getCites()),
                    is.getSameness().getAre().stream().map(is2 ->
                        expd(line(is2.getCertainty(),is2.getPersona(),is2.getPersona().getCites(),is2.getNotes()))).collect(toList())))
            .collect(toList());

        return prePage(stg,
            expd(Line.blank(), asList(
                expd(line(persona.getName())),
                expd(line(persona.getCites()).withLabel("source")),
                expd(blank().withLabel("extracted events"), re),
                expd(blank().withLabel("cross references"), rx))));
    }

    private static String getCitationPagePre(final STGroupFile stg, final Citation citation) {
        return prePage(stg,
            expd(line(citation.getDisplay()),
                citation.getPersonae().stream()
                    .flatMap(persona -> persona.getRoles().stream())
                    .map(Role::getEvent)
                    .distinct()
                    .sorted()
                    .map(Genealdb::getEventDisplay)
                    .collect(Collectors.toList())));
    }

    private static String prePage(final STGroupFile stg, final Expandable x) {
        return stg.getInstanceOf("prepage").add("x", x).render();
    }

    private static Expandable getEventDisplay(final Event event) {
        return expd(line(event.getDisplay()),
            event.getPlayers().stream()
                .map(role -> expd(line(role.getPersona()).withLabel(role.getDisplay())))
                .collect(toList()));
    }

    private static Long getId(final Request req, final Long idDefault) {
        return Long.valueOf(req.queryParamOrDefault("id", idDefault.toString()));
    }

    private static Class getClass(final Request req, final Class classDefault) throws ClassNotFoundException {
        return Class.forName(req.queryParamOrDefault("entity", classDefault.getName()));
    }

    private static void create(final Session session) {
        session.save(buildCitation());
    }

    //private static void read(final Session session) {
    //    STGroupFile stg = new STGroupFile("page.stg");
    //    final Collection<Citation> citations = session.loadAll(Citation.class, -1);
    //    citations.forEach(citation -> {
    //        //dumpByEvent(citation, stg);
    //    });
    //}
    //
    //private static void dumpByPersona(final Citation citation) {
    //    final PrintWriter p = new PrintWriter(System.out, true);
    //    final String INDENT = "    ";
    //
    //    p.println(citation.getDisplay());
    //    citation.getPersonae().forEach(persona -> {
    //        p.println(INDENT + persona.getName());
    //        persona.getRoles().forEach(role -> {
    //            p.println(INDENT + INDENT + role.getDisplay() + " in " + role.getEvent().getDisplay());
    //        });
    //    });
    //}
    //
    //private static void dumpByEvent(final Citation citation, final STGroupFile stg) {
    //    final PrintWriter p = new PrintWriter(System.out, true);
    //    final String INDENT = "    ";
    //    final Set<Event> events = citation.getPersonae().stream().flatMap(persona -> persona.getRoles().stream()).map(Role::getEvent).collect(Collectors.toSet());
    //
    //    p.println("========================================================");
    //    p.println(citation.getDisplay());
    //    events.stream().sorted().forEach(event -> {
    //        p.println(INDENT + event.getDisplay());
    //        event.getPlayers().forEach(role -> {
    //            p.println(INDENT + INDENT + role.getPersona().getName() + " (" + role.getDisplay() + ")");
    //        });
    //    });
    //
    //    p.println("-------------------------");
    //
    //    final List re = new ArrayList();
    //    events.stream().sorted().forEach(event -> {
    //        final List rp = new ArrayList();
    //        event.getPlayers().forEach(role -> {
    //            rp.add(role.getPersona().getName() + " (" + role.getDisplay() + ")");
    //        });
    //        re.add(new Expandable(event.getDisplay(), rp));
    //    });
    //    final Expandable preCita = new Expandable(citation.getDisplay(),re);
    //
    //    p.print(
    //        stg.getInstanceOf("prePage")
    //            .add("x", preCita)
    //            .render());
    //    p.println("========================================================");
    //}

    private static Set<Citation> buildCitation() {
        final Citation birthCertificate = new Citation("birth certificate of Christopher Alan Mosher", URI.create("http://mosher.mine.nu/sources/mosher_chris_birthcert"));

        final Persona chris = new Persona(birthCertificate, "Christopher Alan /Mosher/");
        final Persona linda = new Persona(birthCertificate, "Linda Mosher");
        final Persona barry = new Persona(birthCertificate, "Barry Rexford /Mosher/");

        final Certainty certaintyOfDate = new Certainty(10L);
        final Certainty certaintyOfPlace = new Certainty(10L);
        final String notes = "";

        final Event birthOfChris = new Event(Day.ofIso(1966, 7, 3), "Radford", "birth", "of Chris", certaintyOfDate, certaintyOfPlace, notes);
        new Role(chris, birthOfChris, "newborn", MUST, notes);
        new Role(barry, birthOfChris, "father", new Certainty(8), notes);
        new Role(linda, birthOfChris, "mother", MUST, notes);

        final Event nameOfChris = new Event(Day.ofIso(1966, 7, 3), "Radford", "used name", "Christopher Alan /Mosher/", certaintyOfDate, certaintyOfPlace, notes);
        new Role(chris, nameOfChris, "subject", MUST, notes);

        final Event nameOfBarry = new Event(Day.ofIso(1966, 7, 3), "Radford", "used name", "Barry Rexford /Mosher/", certaintyOfDate, certaintyOfPlace, notes);
        new Role(barry, nameOfBarry, "subject", MUST, notes);

        final Event birthOfBarry = new Event(Day.ofYearIso(1939).withCirca(), "New York", "birth", "of Barry", certaintyOfDate, certaintyOfPlace, notes);
        new Role(barry, birthOfBarry, "newborn", MUST, notes);

        final Event nameOfLinda = new Event(Day.ofIso(1966, 7, 3), "Radford", "used name", "Linda Mosher", certaintyOfDate, certaintyOfPlace, notes);
        new Role(linda, nameOfLinda, "subject", MUST, notes);

        final Event birthOfLinda = new Event(Day.ofYearIso(1941).withCirca(), "New York", "birth", "of Linda", certaintyOfDate, certaintyOfPlace, notes);
        new Role(linda, birthOfLinda, "newborn", MUST, notes);

        final Event marriageOfBarryAndLinda = new Event(Day.ofYearIso(1963).withCirca(), "New York", "marriage", "of Barry and Linda", certaintyOfDate, certaintyOfPlace, notes);
        new Role(barry, marriageOfBarryAndLinda, "husband", new Certainty(5), notes);
        new Role(linda, marriageOfBarryAndLinda, "wife", new Certainty(5), notes);

        final Event residenceInPulaski = new Event(Day.ofMonthIso(1966,7), "Pulaski", "residence", "of Mosher family in Pulaski", certaintyOfDate, certaintyOfPlace, notes);
        new Role(barry, residenceInPulaski, "subject", MUST, notes);
        new Role(linda, residenceInPulaski, "subject", MUST, notes);
        new Role(chris, residenceInPulaski, "subject", MUST, notes);



        final Citation driversLicense = new Citation("driver's license of Christopher Alan Mosher", URI.create("http://mosher.mine.nu/sources/mosher_chris_driverslicense"));
        final Persona chris2 = new Persona(driversLicense, "Christopher Alan /Mosher/");
        final Event residenceInShelton = new Event(Day.ofYearIso(2016), "Shelton", "residence", "of Mosher family in Shelton", certaintyOfDate, certaintyOfPlace, notes);
        new Role(chris2, residenceInShelton, "subject", MUST, notes);



        final Citation myRootPedigreeDatabase = new Citation("Barry and Linda Mosher Recent Genealogy", URI.create("http://mosher.mine.nu/sources/local/root"));
        final Sameness me = new Sameness(myRootPedigreeDatabase, "same full name");
        new Is(me, chris, MUST, notes);
        new Is(me, chris2, MUST, notes);



        return Set.of(myRootPedigreeDatabase, driversLicense, birthCertificate);
    }
}
