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
        final Place svUsa = new Place("United States of America", plus("86000000+"), null, "country US (1776-)");

        final PlaceChange indepUSA = new PlaceChange(Day.ofIso(1776, 7, 4), "Declaration of Independence of USA");

        final Place stNy = new Place("State of New York", plus("87J50000+"), null, "originally a colony");
        new Transfer().of(stNy).to(svUsa).during(indepUSA);
        final Place stVa = new Place("Commonwealth of Virginia", plus("87930000+"), null, "commonly known as a state");
        new Transfer().of(stVa).to(svUsa).during(indepUSA);
        final Place stCt = new Place("State of Connecticut", plus("87H90000+"), null, "Nutmeg state");
        new Transfer().of(stCt).to(svUsa).during(indepUSA);

        final PlaceChange foundedChenango = new PlaceChange(1798, "Founding of Chenango County");
        final Place coChenango = new Place("County of Chenango", plus("87J6G900+"), null, "");
        new Transform().to(coChenango).during(foundedChenango);
        new Transfer().of(coChenango).to(stNy).during(foundedChenango);

        final PlaceChange splitChenango = new PlaceChange(1806, "Chenango County split");
        final Place coMadison = new Place("County of Madison", plus("87J6W800+"), null, "");
        new Transform().from(coChenango).to(coChenango).to(coMadison).during(splitChenango);
        new Transfer().of(coMadison).to(stNy).during(splitChenango);

        final PlaceChange foundedEarlville = new PlaceChange(1792, "Founding of Earlville (Madison Forks)");
        final Place pMadisonForks = new Place("Madison Forks", plus("87J6PFQ3+"), null, "");
        new Transform().to(pMadisonForks).during(foundedEarlville);
        new Transfer().of(pMadisonForks).to(coChenango).during(foundedChenango);
        new Transfer().of(pMadisonForks).to(coMadison).during(splitChenango);
        final PlaceChange nameEarlville = new PlaceChange(1835, "Rename Madison Forks to Earlville (upon completion of Chenango Canal to honor Jonas Earl)");
        final Place pEarlville = new Place("Earlville", plus("87J6PFQ3+"), null, "");
        new Transform().from(pMadisonForks).to(pEarlville).during(nameEarlville);
        final PlaceChange incEarlville = new PlaceChange(1887, "Incorporation of Earlville");
        final Place vilEarlville = new Place("Village of Earlville", plus("87J6PFQ3+"), null, "Earlville is unusual in that it currently (2020) lies within 2 counties");
        new Transform().from(pEarlville).to(vilEarlville).during(incEarlville);

        final PlaceChange indepenentCitiesVirginia = new PlaceChange(1871, "Virginia Constitution");
        final Place radford = new Place("Radford, VA", plus("869X4C00+"), null, "independent city");
        new Transform().to(radford).during(indepenentCitiesVirginia);
        new Transfer().of(radford).to(stVa).during(indepenentCitiesVirginia);

        final PlaceChange incPulaski = new PlaceChange(1886, "Incorporation of Pulaski");
        final Place pulaski = new Place("Pulaski, VA", plus("869X2600+"), null, "");
        new Transform().to(pulaski).during(incPulaski);
        new Transfer().of(pulaski).to(stVa).during(incPulaski);

        final Place pColonies = new Place("British colonies in America", plus("87000000+"), null, "");
        final PlaceChange pColonieEstab = new PlaceChange(Day.ofIso(1607,5,14), "Jamestown, Virginia, established");
        new Transform().to(pColonies).during(pColonieEstab);

        final Place pNewEnglandCols = new Place("New England colonies", plus("87JC0000+"),null, "");
        final PlaceChange pNewEnglandColsEstab = new PlaceChange(Day.ofIso(1620, 11, 21), "Mayflower Compact");
        new Transform().to(pNewEnglandCols).during(pNewEnglandColsEstab);

        final Place pNewEnglandConf = new Place("United Colonies of New England", plus("87JC0000+"),null, "");
        final PlaceChange pUnitedColsOfNewEngConf = new PlaceChange(Day.ofIso(1643, 5, 19), "Articles of Confederation of the United Colonies of New England");
        new Transform().from(pNewEnglandCols).to(pNewEnglandConf).during(pUnitedColsOfNewEngConf);
        new Transfer().of(pNewEnglandConf).to(pColonies).during(pUnitedColsOfNewEngConf);

        final Place pNewEnglandDom = new Place("Dominion of New England", plus("87JC0000+"),null, "");
        final PlaceChange pNewEnglandDomEstab = new PlaceChange(Day.ofIso(1686, 5, 25), "Joseph Dudley took charge of Massachusetts");
        new Transform().from(pNewEnglandConf).to(pNewEnglandDom).during(pNewEnglandDomEstab);
        new Transfer().of(pNewEnglandDom).to(pColonies).during(pNewEnglandDomEstab);

        final Place pNewEnglandCols2 = new Place("New England colonies", plus("87JC0000+"),null, "");
        final PlaceChange pNewEnglandCols2Estab = new PlaceChange(Day.ofIso(1689, 4, 18), "Boston Revolt");
        new Transform().from(pNewEnglandDom).to(pNewEnglandCols2).during(pNewEnglandCols2Estab);
        new Transfer().of(pNewEnglandCols2).to(pColonies).during(pNewEnglandCols2Estab);

        final Place pNewEnglandReg = new Place("New England region", plus("87JC0000+"),null, "");
        new Transform().from(pNewEnglandCols2).to(pNewEnglandReg).during(indepUSA);
        new Transfer().of(pNewEnglandReg).to(svUsa).during(indepUSA);

        new Transform().from(pColonies).to(svUsa).during(indepUSA);

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

        new Transfer().of(stCt).to(pNewEnglandReg).during(indepUSA);

        final Place pFairfieldCo = new Place("County of Fairfield", plus("87H88M00+"), null, "");
        new Transfer().of(pFairfieldCo).to(stCt);

        final Place pShelton = new Place("City of Shelton", plus("87H87V00+"), null, "");
        new Transfer().of(pShelton).to(pFairfieldCo);

        final Place brookPine = new Place("41 Brook Pine Drive, Shelton, CT", plus("87H88V8F+HF"), null, "owners: Bacchiocchi; Mosher");
        new Transfer().of(brookPine).to(pShelton);






        final Citation birthCertificate = new Citation("birth certificate of Christopher Alan Mosher", URI.create("http://mosher.mine.nu/sources/mosher_chris_birthcert"));

        final Persona chris = new Persona(birthCertificate, "Christopher Alan /Mosher/", "me");
        final Persona linda = new Persona(birthCertificate, "Linda Mosher", "maiden name Disosway");
        final Persona barry = new Persona(birthCertificate, "Barry Rexford /Mosher/", "");

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
        final Persona chris2 = new Persona(driversLicense, "Christopher Alan /Mosher/", "me");
        final Event residenceInShelton = new Event(Day.ofYearIso(2016), brookPine, "residence", "of Mosher family in Shelton", certaintyOfDate, certaintyOfPlace, notes);
        new Role(chris2, residenceInShelton, "subject", MUST, notes);



        final Citation myRootPedigreeDatabase = new Citation("Barry and Linda Mosher Recent Genealogy", URI.create("http://mosher.mine.nu/sources/local/root"));
        final Sameness me = new Sameness(myRootPedigreeDatabase, "same full name", "these are both me");
        new Is(me, chris, MUST, notes);
        new Is(me, chris2, MUST, notes);




        final HashSet set = new HashSet();
        set.add(myRootPedigreeDatabase);
        set.add(driversLicense);
        set.add(birthCertificate);
        return set;
    }

    private static OpenLocationCode plus(String s) {
        return new OpenLocationCode(s);
    }

    private static OpenLocationCode buildLatLong(final double latitude, final double longitude) {
        return new OpenLocationCode(longitude, latitude, Place.CODE_PRECISION_GENEALOGICAL);
    }
}
