package nu.mine.mosher.genealdb;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.threeten.extra.chrono.JulianChronology;

public class DayTest {
    @Test
    void nominal() {
//        p(d(JapaneseChronology.INSTANCE));
//        p(d(MinguoChronology.INSTANCE));
//        p(d(ThaiBuddhistChronology.INSTANCE));
//        p(d(HijrahChronology.INSTANCE));
//        p(d(JulianChronology.INSTANCE));
//        p(d(IsoChronology.INSTANCE));

        System.out.println(""+new Day(1966));
        System.out.println(""+new Day(1966, 7));
        System.out.println(""+new Day(1966, 7, 3));
        System.out.println(""+new Day(-7));
        System.out.println(""+new Day(Optional.of(1632), Optional.of(2), Optional.of(14), false, JulianChronology.INSTANCE));
        System.out.println(""+new Day(Optional.of(1984), Optional.of(6), Optional.empty(), true));
        System.out.println(""+new Day(Optional.empty(), Optional.empty(), Optional.empty()));
    }
}
