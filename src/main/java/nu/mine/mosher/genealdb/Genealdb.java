package nu.mine.mosher.genealdb;

import java.io.PrintWriter;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import nu.mine.mosher.genealdb.model.entity.*;
import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.Day;
import org.neo4j.ogm.config.*;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import static nu.mine.mosher.genealdb.model.type.Certainty.MUST;

public class Genealdb {
    public static void main(final String... args) throws InterruptedException, ClassNotFoundException {
        if (args.length != 1) {
            throw new IllegalArgumentException("usage: genealdb r|c");
        }

        final ConfigurationSource configSourceDb = new ClasspathConfigurationSource("ogm.properties");
        final Configuration configDb = new Configuration.Builder(configSourceDb).build();
        final SessionFactory sessionFactory = new SessionFactory(configDb, Event.class.getPackageName());
        try {
            final Session session = sessionFactory.openSession();
            if (args[0].equalsIgnoreCase("c")) {
                create(session);
            } else if (args[0].equalsIgnoreCase("r")) {
                read(session);
            }
        } finally {
            sessionFactory.close();
        }

        System.out.flush();
        System.err.flush();
    }

    private static void create(final Session session) {
        session.save(buildCitation());
    }

    private static void read(final Session session) {
        final Collection<Citation> citations = session.loadAll(Citation.class, -1);
        citations.forEach(citation -> {
            //dumpByPersona(citation);
            dumpByEvent(citation);
        });
    }

    private static void dumpByPersona(final Citation citation) {
        final PrintWriter p = new PrintWriter(System.out, true);
        final String INDENT = "    ";

        p.println(citation.getDisplay());
        citation.getPersonae().forEach(persona -> {
            p.println(INDENT + persona.getName());
            persona.getRoles().forEach(role -> {
                p.println(INDENT + INDENT + role.getDisplay() + " in " + role.getEvent().getDisplay());
            });
        });
    }

    private static void dumpByEvent(final Citation citation) {
        final PrintWriter p = new PrintWriter(System.out, true);
        final String INDENT = "    ";

        p.println(citation.getDisplay());
        final Set<Event> events = citation.getPersonae().stream().flatMap(persona -> persona.getRoles().stream()).map(Role::getEvent).collect(Collectors.toSet());
        events.stream().sorted().forEach(event -> {
            p.println(INDENT + event.getDisplay());
            event.getPlayers().forEach(role -> {
                p.println(INDENT + INDENT + role.getPersona().getName() + " (" + role.getDisplay() + ")");
            });
        });
    }

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
