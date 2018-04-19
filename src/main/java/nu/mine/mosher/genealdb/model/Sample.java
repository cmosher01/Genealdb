package nu.mine.mosher.genealdb.model;

import java.net.URI;
import java.time.LocalDate;
import java.util.Set;
import nu.mine.mosher.genealdb.model.entity.*;
import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.Day;

import static nu.mine.mosher.genealdb.model.type.Certainty.MUST;

public class Sample {
    public static Set buildEntities() {
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


        final Place pColonies = new Place("British colonies in America");
        pColonies.self(LocalDate.of(1607, 5, 4));
        final Place pNewEnglandCols = new Place("New England colonies");
        pNewEnglandCols.self(LocalDate.of(1620, 4, 10));
        pNewEnglandCols.in(pColonies);
        final Place pNewEnglandConf = new Place("United Colonies of New England");
        pNewEnglandConf.in(pColonies);
        pNewEnglandCols.became(pNewEnglandConf, LocalDate.of(1643, 5, 19));
        final Place pNewEnglandDom = new Place("Dominion of New England");
        pNewEnglandDom.in(pColonies);
        pNewEnglandConf.became(pNewEnglandDom, LocalDate.of(1686, 5, 25));
        final Place pNewEnglandReg = new Place("New England region");
        pNewEnglandReg.in(pColonies);
        pNewEnglandDom.became(pNewEnglandReg, LocalDate.of(1689, 4, 18));

        final Place pUSA = new Place("United States of America");
        pColonies.became(pUSA, LocalDate.of(1776, 7, 4));
        pNewEnglandReg.in(pUSA);

        final Place pConnCol = new Place("Colony of Connecticut");
        pConnCol.self(LocalDate.of(1636,3,3));
        pConnCol.in(pNewEnglandCols);
        pConnCol.in(pNewEnglandConf);
        pConnCol.in(pNewEnglandDom);
        pConnCol.in(pNewEnglandReg);
        final Place pNewHavenCol = new Place("Colony of New Haven");
        pNewHavenCol.self(LocalDate.of(1637,6,26));
        pNewHavenCol.in(pNewEnglandCols);
        pNewHavenCol.in(pNewEnglandConf);
        pNewHavenCol.became(pConnCol, LocalDate.of(1664, 12, 14));
        final Place pSaybrookCol = new Place("Colony of Saybrook");
        pSaybrookCol.self(LocalDate.of(1635, 11, 24));
        pSaybrookCol.in(pNewEnglandCols);
        pSaybrookCol.became(pConnCol, LocalDate.of(1644, 12, 5));

        final Place pConn = new Place("State of Connecticut");
        pConn.in(pNewEnglandReg);
        pConnCol.became(pConn, LocalDate.of(1788, 1, 9));
        return Set.of(myRootPedigreeDatabase, driversLicense, birthCertificate, pConn, pConnCol, pSaybrookCol, pNewHavenCol);
    }
}
