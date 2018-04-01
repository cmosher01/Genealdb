package nu.mine.mosher.genealdb;

import com.google.common.base.Strings;
import com.google.common.collect.Comparators;
import com.google.common.math.Stats;
import java.time.DateTimeException;
import java.time.chrono.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;

import static java.util.Comparator.comparing;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Deprecated
public class DayOld implements Comparable<DayOld> {
    public static final Chronology DEFAULT_CHRONOLOGY = Chronology.ofLocale(Locale.getDefault());

    private final boolean circa;
    private final Chronology chronology;
    private final Optional<Integer> prolepticYear;
    private final Optional<Integer> month;
    private final Optional<Integer> day;

    private final transient ChronoUnit precision;
    private final transient Optional<ChronoLocalDate> cld;
    private final transient boolean otherEra;

    public DayOld(final int prolepticYear) {
        this(Optional.of(prolepticYear), Optional.empty(), Optional.empty());
    }

    public DayOld(final int prolepticYear, final int month) {
        this(Optional.of(prolepticYear), Optional.of(month), Optional.empty());
    }

    public DayOld(final int prolepticYear, final int month, final int day) {
        this(Optional.of(prolepticYear), Optional.of(month), Optional.of(day));
    }

    public DayOld(final Optional<Integer> prolepticYear, final Optional<Integer> month, final Optional<Integer> day) {
        this(prolepticYear, month, day, false);
    }

    public DayOld(final Optional<Integer> prolepticYear, final Optional<Integer> month, final Optional<Integer> day, final boolean circa) {
        this(prolepticYear, month, day, circa, DEFAULT_CHRONOLOGY);
    }

    public DayOld(final Optional<Integer> prolepticYear, final Optional<Integer> month, final Optional<Integer> day, final boolean circa, final Chronology chronology) {
        this.chronology = chronology;

        this.prolepticYear = prolepticYear;
        this.month = this.prolepticYear.isPresent() ? month : Optional.empty();
        this.day = this.month.isPresent() ? day : Optional.empty();

        this.precision = calcPrec();

        this.circa = circa && prec(ChronoUnit.YEARS);

        this.cld = calcCld();

        this.otherEra = this.cld.isPresent() && !this.cld.get().getEra().equals(this.chronology.dateNow().getEra());
    }



    private ChronoUnit calcPrec() {
        if (!this.prolepticYear.isPresent()) {
            return ChronoUnit.FOREVER;
        }
        if (!this.month.isPresent()) {
            return ChronoUnit.YEARS;
        }
        if (!this.day.isPresent()) {
            return ChronoUnit.MONTHS;
        }
        return ChronoUnit.DAYS;
    }

    private Optional<ChronoLocalDate> calcCld() {
        switch (this.precision) {
            case DAYS:
                return Optional.of(this.chronology.date(prolepticYear.get(), month.get(), day.get()));
            case MONTHS:
                return Optional.of(this.chronology.date(prolepticYear.get(), month.get(), average(ChronoField.DAY_OF_MONTH)));
            case YEARS:
                return Optional.of(this.chronology.dateYearDay(prolepticYear.get(), average(ChronoField.DAY_OF_YEAR)));
            default:
                return Optional.empty();
        }
    }

    private int average(final ChronoField field) {
        final ValueRange range = this.chronology.range(field);
        final int a = (int) Math.round(Stats.meanOf(range.getMinimum(), range.getMaximum()));
        if (range.isValidValue(a)) {
            return a;
        }
        if (range.isValidValue(1)) {
            return 1;
        }
        throw new DateTimeException("Cannot determine average value for range.");
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof DayOld)) {
            return false;
        }
        final DayOld that = (DayOld) object;
        return Objects.equals(this.circa, that.circa) &&
            Objects.equals(this.chronology, that.chronology) &&
            Objects.equals(this.prolepticYear, that.prolepticYear) &&
            Objects.equals(this.month, that.month) &&
            Objects.equals(this.day, that.day);
    }

    @Override
    public int hashCode() {
        return this.cld.hashCode();
    }

    @Override
    public int compareTo(final DayOld that) {
        return Comparators.emptiesLast(ChronoLocalDate.timeLineOrder()).compare(this.cld, that.cld);
    }

    public static final int MAX_FIELD_WIDTH = 19;
    public static final int MIN_YEAR_FIELD_WIDTH = 4;
    public static final int MIN_MONTH_FIELD_WIDTH = 2;
    public static final int MIN_DAY_FIELD_WIDTH = 2;
    public static final char FIELD_SEPARATOR = '\u002d';
    public static final char ERA_FIELD_SEPARATOR = '\u0020';
    public static final char CIRCA_INDICATOR = '\u007e';
    public static final char UNKNOWN_INDICATOR = '\u0058';

    @Override
    public String toString() {
        final DateTimeFormatterBuilder b = bCal(bCirca(bDay(bMonth(bYear(bEra(new DateTimeFormatterBuilder()))))));
        return b.toFormatter().format(this.cld.orElse(noDate()));
    }

    private DateTimeFormatterBuilder bEra(DateTimeFormatterBuilder b) {
        if (this.otherEra) {
            b = b.appendText(ChronoField.ERA, TextStyle.SHORT).appendLiteral(ERA_FIELD_SEPARATOR);
        }
        return b;
    }

    private DateTimeFormatterBuilder bYear(DateTimeFormatterBuilder b) {
        if (prec(ChronoUnit.YEARS)) {
            b = b.appendValue(ChronoField.YEAR_OF_ERA, MIN_YEAR_FIELD_WIDTH, MAX_FIELD_WIDTH, SignStyle.NORMAL);
        } else {
            b = b.appendLiteral(unk(MIN_YEAR_FIELD_WIDTH));
        }
        b = b.appendLiteral(FIELD_SEPARATOR);
        return b;
    }

    private DateTimeFormatterBuilder bMonth(DateTimeFormatterBuilder b) {
        if (prec(ChronoUnit.MONTHS)) {
            b = b.appendValue(ChronoField.MONTH_OF_YEAR, MIN_MONTH_FIELD_WIDTH, MAX_FIELD_WIDTH, SignStyle.NORMAL);
        } else {
            b = b.appendLiteral(unk(MIN_MONTH_FIELD_WIDTH));
        }
        b = b.appendLiteral(FIELD_SEPARATOR);
        return b;
    }

    private DateTimeFormatterBuilder bDay(DateTimeFormatterBuilder b) {
        if (prec(ChronoUnit.DAYS)) {
            b = b.appendValue(ChronoField.DAY_OF_MONTH, MIN_DAY_FIELD_WIDTH, MAX_FIELD_WIDTH, SignStyle.NORMAL);
        } else {
            b = b.appendLiteral(unk(MIN_DAY_FIELD_WIDTH));
        }
        return b;
    }

    private DateTimeFormatterBuilder bCirca(DateTimeFormatterBuilder b) {
        if (this.circa) {
            b = b.appendLiteral(CIRCA_INDICATOR);
        }
        return b;
    }

    private DateTimeFormatterBuilder bCal(DateTimeFormatterBuilder b) {
        if (!this.chronology.equals(DEFAULT_CHRONOLOGY)) {
            b = b.appendLiteral(" (" + this.chronology.getId() + ")");
        }
        return b;
    }

    private static String unk(final int width) {
        return Strings.repeat(Character.toString(UNKNOWN_INDICATOR), width);
    }

    private boolean prec(final ChronoUnit u) {
        return comparing(ChronoUnit::getDuration).compare(this.precision, u) <= 0;
    }

    private ChronoLocalDate noDate() {
        return new ChronoLocalDate() {
            @Override
            public Chronology getChronology() {
                return DayOld.this.chronology;
            }

            @Override
            public int lengthOfMonth() {
                return 0;
            }

            @Override
            public long until(final Temporal endExclusive, final TemporalUnit unit) {
                return 0;
            }

            @Override
            public ChronoPeriod until(final ChronoLocalDate endDateExclusive) {
                return null;
            }

            @Override
            public long getLong(final TemporalField field) {
                return 0;
            }
        };
    }
}
