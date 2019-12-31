package nu.mine.mosher.genealdb.model;

import com.google.openlocationcode.OpenLocationCode;
import nu.mine.mosher.genealdb.model.entity.conclude.*;
import nu.mine.mosher.genealdb.model.entity.extract.*;
import nu.mine.mosher.genealdb.model.entity.place.*;
import nu.mine.mosher.genealdb.model.entity.source.Citation;
import nu.mine.mosher.genealdb.model.type.*;

import java.net.URI;
import java.util.*;

import static nu.mine.mosher.genealdb.model.type.Certainty.MUST;

public class Sample {
    public static Set buildEntities() {
        final Place svUsa = new Place("United States of America", buildLatLong(44.674000, -103.853100));

        final PlaceChange constitutionUsa = new PlaceChange(1788, "Constitution of USA");

        final Place stNy = new Place("State of New York", buildLatLong(42.9212335, -75.5965432));
        new Transfer().of(stNy).to(svUsa).during(constitutionUsa);
        final Place stVa = new Place("Commonwealth of Virginia", buildLatLong(37.5108711, -78.6663566));
        new Transfer().of(stVa).to(svUsa).during(constitutionUsa);
        final Place stCt = new Place("State of Connecticut", buildLatLong(41.5751642, -72.7382577));
        new Transfer().of(stCt).to(svUsa).during(constitutionUsa);

        final PlaceChange foundedChenango = new PlaceChange(1798, "Founding of Chenango County");
        final Place coChenango = new Place("County of Chenango", null);
        new Transform().to(coChenango).during(foundedChenango);
        new Transfer().of(coChenango).to(stNy).during(foundedChenango);

        final PlaceChange splitChenango = new PlaceChange(1806, "Chenango County split");
        final Place coMadison = new Place("County of Madison", null);
        new Transform().from(coChenango).to(coChenango).to(coMadison).during(splitChenango);
        new Transfer().of(coMadison).to(stNy).during(splitChenango);

        final PlaceChange foundedEarlville = new PlaceChange(1792, "Founding of Earlville (Madison Forks)");
        final Place pMadisonForks = new Place("Madison Forks", null);
        new Transform().to(pMadisonForks).during(foundedEarlville);
        new Transfer().of(pMadisonForks).to(coChenango).during(foundedChenango);
        new Transfer().of(pMadisonForks).to(coMadison).during(splitChenango);
        final PlaceChange nameEarlville = new PlaceChange(1835, "Rename Madison Forks to Earlville (upon completion of Chenango Canal to honor Jonas Earl)");
        final Place pEarlville = new Place("Earlville", null);
        new Transform().from(pMadisonForks).to(pEarlville).during(nameEarlville);
        final PlaceChange incEarlville = new PlaceChange(1887, "Incorporation of Earlville");
        final Place vilEarlville = new Place("Village of Earlville", null);
        new Transform().from(pEarlville).to(vilEarlville).during(incEarlville);

        final PlaceChange indepenentCitiesVirginia = new PlaceChange(1871, "Virginia Constitution");
        final Place radford = new Place("Radford, VA", buildLatLong(37.1275, -80.569444));
        new Transform().to(radford).during(indepenentCitiesVirginia);
        new Transfer().of(radford).to(stVa).during(indepenentCitiesVirginia);

        final PlaceChange incPulaski = new PlaceChange(1886, "Incorporation of Pulaski");
        final Place pulaski = new Place("Pulaski, VA", buildLatLong(37.05, -80.772222));
        new Transform().to(pulaski).during(incPulaski);
        new Transfer().of(pulaski).to(stVa).during(incPulaski);

        final Place brookPine = new Place("41 Brook Pine Drive, Shelton, CT", buildLatLong(41.316409, -73.126310));









        final Citation birthCertificate = new Citation("birth certificate of Christopher Alan Mosher", URI.create("http://mosher.mine.nu/sources/mosher_chris_birthcert"));

        final Persona chris = new Persona(birthCertificate, "Christopher Alan /Mosher/");
        final Persona linda = new Persona(birthCertificate, "Linda Mosher");
        final Persona barry = new Persona(birthCertificate, "Barry Rexford /Mosher/");

        final Certainty certaintyOfDate = new Certainty(10L);
        final Certainty certaintyOfPlace = new Certainty(10L);
        final String notes = "";


        final Event birthOfChris = new Event(Day.ofIso(1966, 7, 3), radford, "birth", "of Chris", certaintyOfDate, certaintyOfPlace, notes);
        new Role(chris, birthOfChris, "newborn", MUST, notes);
        new Role(barry, birthOfChris, "father", new Certainty(8), notes);
        new Role(linda, birthOfChris, "mother", MUST, notes);

        final Event nameOfChris = new Event(Day.ofIso(1966, 7, 3), radford, "used name", "Christopher Alan /Mosher/", certaintyOfDate, certaintyOfPlace, notes);
        new Role(chris, nameOfChris, "subject", MUST, notes);

        final Event nameOfBarry = new Event(Day.ofIso(1966, 7, 3), radford, "used name", "Barry Rexford /Mosher/", certaintyOfDate, certaintyOfPlace, notes);
        new Role(barry, nameOfBarry, "subject", MUST, notes);

        final Event birthOfBarry = new Event(Day.ofYearIso(1939).withCirca(), null, "birth", "of Barry", certaintyOfDate, certaintyOfPlace, notes);
        new Role(barry, birthOfBarry, "newborn", MUST, notes);

        final Event nameOfLinda = new Event(Day.ofIso(1966, 7, 3), radford, "used name", "Linda Mosher", certaintyOfDate, certaintyOfPlace, notes);
        new Role(linda, nameOfLinda, "subject", MUST, notes);

        final Event birthOfLinda = new Event(Day.ofYearIso(1941).withCirca(), null, "birth", "of Linda", certaintyOfDate, certaintyOfPlace, notes);
        new Role(linda, birthOfLinda, "newborn", MUST, notes);

        final Event marriageOfBarryAndLinda = new Event(Day.ofYearIso(1963).withCirca(), null, "marriage", "of Barry and Linda", certaintyOfDate, certaintyOfPlace, notes);
        new Role(barry, marriageOfBarryAndLinda, "husband", new Certainty(5), notes);
        new Role(linda, marriageOfBarryAndLinda, "wife", new Certainty(5), notes);

        final Event residenceInPulaski = new Event(Day.ofMonthIso(1966,7), pulaski, "residence", "of Mosher family in Pulaski", certaintyOfDate, certaintyOfPlace, notes);
        new Role(barry, residenceInPulaski, "subject", MUST, notes);
        new Role(linda, residenceInPulaski, "subject", MUST, notes);
        new Role(chris, residenceInPulaski, "subject", MUST, notes);



        final Citation driversLicense = new Citation("driver's license of Christopher Alan Mosher", URI.create("http://mosher.mine.nu/sources/mosher_chris_driverslicense"));
        final Persona chris2 = new Persona(driversLicense, "Christopher Alan /Mosher/");
        final Event residenceInShelton = new Event(Day.ofYearIso(2016), brookPine, "residence", "of Mosher family in Shelton", certaintyOfDate, certaintyOfPlace, notes);
        new Role(chris2, residenceInShelton, "subject", MUST, notes);



        final Citation myRootPedigreeDatabase = new Citation("Barry and Linda Mosher Recent Genealogy", URI.create("http://mosher.mine.nu/sources/local/root"));
        final Sameness me = new Sameness(myRootPedigreeDatabase, "same full name");
        new Is(me, chris, MUST, notes);
        new Is(me, chris2, MUST, notes);


        //final Place pColonies = new Place("British colonies in America");
        //pColonies.self(LocalDate.of(1607, 5, 4));
        //
        //final Place pUSA = new Place("United States of America");
        //pColonies.became(pUSA, LocalDate.of(1776, 7, 4));
        //
        //final Place pNewEnglandCols = new Place("New England colonies");
        //pNewEnglandCols.self(LocalDate.of(1620, 4, 10));
        //pNewEnglandCols.in(pColonies);
        //
        //final Place pNewEnglandConf = new Place("United Colonies of New England");
        //pNewEnglandConf.in(pColonies);
        //pNewEnglandCols.became(pNewEnglandConf, LocalDate.of(1643, 5, 19));
        //
        //final Place pNewEnglandDom = new Place("Dominion of New England");
        //pNewEnglandDom.in(pColonies);
        //pNewEnglandConf.became(pNewEnglandDom, LocalDate.of(1686, 5, 25));
        //
        //final Place pNewEnglandCols2 = new Place("New England colonies");
        //pNewEnglandCols2.in(pColonies);
        //pNewEnglandDom.became(pNewEnglandCols2, LocalDate.of(1689, 4, 18));
        //
        //final Place pNewEnglandReg = new Place("New England region");
        //pNewEnglandReg.in(pUSA);
        //pNewEnglandCols2.became(pNewEnglandReg, LocalDate.of(1776, 7, 4));
        //
        //final Place pConnCol = new Place("Colony of Connecticut");
        //pConnCol.self(LocalDate.of(1636,3,3));
        //pConnCol.in(pNewEnglandCols);
        //pConnCol.in(pNewEnglandConf);
        //pConnCol.in(pNewEnglandDom);
        //pConnCol.in(pNewEnglandCols2);
        //final Place pNewHavenCol = new Place("Colony of New Haven");
        //pNewHavenCol.self(LocalDate.of(1637,6,26));
        //pNewHavenCol.in(pNewEnglandCols);
        //pNewHavenCol.in(pNewEnglandConf);
        //pNewHavenCol.became(pConnCol, LocalDate.of(1664, 12, 14));
        //final Place pSaybrookCol = new Place("Colony of Saybrook");
        //pSaybrookCol.self(LocalDate.of(1635, 11, 24));
        //pSaybrookCol.in(pNewEnglandCols);
        //pSaybrookCol.became(pConnCol, LocalDate.of(1644, 12, 5));
        //
        //final Place pConn = new Place("State of Connecticut");
        //pConn.in(pNewEnglandReg);
        //pConnCol.became(pConn, LocalDate.of(1788, 1, 9));


        final HashSet set = new HashSet();
        set.add(myRootPedigreeDatabase);
        set.add(driversLicense);
        set.add(birthCertificate);
        return set;
    }

    private static OpenLocationCode buildLatLong(final double latitude, final double longitude) {
        return new OpenLocationCode(longitude, latitude, Place.CODE_PRECISION_GENEALOGICAL);
    }
}
