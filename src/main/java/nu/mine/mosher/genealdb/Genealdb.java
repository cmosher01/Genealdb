package nu.mine.mosher.genealdb;

import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;

public class Genealdb {
    public static void main(final String... args) {
        final Day day = new Day(IsoChronology.INSTANCE.dateNow(), ChronoUnit.DAYS);
        System.out.println(day.toString() + " " + day.toDebugString());
        System.out.flush();
    }
}
