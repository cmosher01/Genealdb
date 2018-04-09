package nu.mine.mosher.genealdb.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.*;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.threeten.extra.chrono.JulianDate;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayTest {
    @Test
    void nominal() {
        final Day uut = show(Day.ofIso(1966, 7, 3));
        assertThat(uut.getDisplay()).isEqualTo("1966-07-03");
    }

    @Test
    void unknown() {
        final Day uut = show(Day.unknownIso(1, 1, 1));
        assertThat(uut.getDisplay()).isEqualTo("XXXX-XX-XX");
    }

    @Test
    void year() {
        final Day uut = show(Day.ofYearIso(1999));
        assertThat(uut.getDisplay()).isEqualTo("1999-XX-XX");
    }

    @Test
    void yearSort() {
        final Day uut = show(Day.ofYearIso(1999));
        assertThat(uut).isLessThan(Day.ofIso(1999, 12, 31));
        assertThat(uut).isGreaterThan(Day.ofIso(1999, 1, 1));
    }

    @Test
    void month() {
        final Day uut = show(Day.ofMonthIso(1999, 4));
        assertThat(uut.getDisplay()).isEqualTo("1999-04-XX");
    }

    @Test
    void circa() {
        final Day uut = show(Day.ofMonthIso(1999, 4).withCirca());
        assertThat(uut.getDisplay()).isEqualTo("1999-04-XX~");
    }

    @Test
    void bc() {
        final Day uut = show(new Day(IsoChronology.INSTANCE.date(IsoEra.BCE, 5, 6, 7), ChronoUnit.DAYS));
        assertThat(uut.getDisplay()).isEqualTo("BC 0005-06-07");
    }

    @Test
    void julian() {
        final LocalDate gregorianGeorgeWashingtonBirth = IsoChronology.INSTANCE.date(1732, 2, 22);
        final Day uut = show(new Day(JulianDate.from(gregorianGeorgeWashingtonBirth), ChronoUnit.DAYS));
        assertThat(uut.getDisplay()).isEqualTo("1732-02-11 (Julian)");
    }

    @Test
    void invalidPrecision() {
        assertThrows(DateTimeException.class, () -> new Day(IsoChronology.INSTANCE.dateNow(), ChronoUnit.HOURS));
    }

    @Test
    void nulls() {
        assertThrows(NullPointerException.class, () -> new Day(null, ChronoUnit.YEARS));
        assertThrows(NullPointerException.class, () -> new Day(IsoChronology.INSTANCE.dateNow(), null));
    }

    static Day show(final Day uut) {
        System.out.println(uut.toString());
        return uut;
    }
}
