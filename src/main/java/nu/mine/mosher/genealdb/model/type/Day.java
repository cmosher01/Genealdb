package nu.mine.mosher.genealdb.model.type;

import com.google.common.base.Strings;
import java.time.DateTimeException;
import java.time.chrono.*;
import java.time.format.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Comparator.comparing;

/**
 * A day (date), with a degree of precision (year, month, day), and possibly a "circa".
 */
public class Day implements Comparable<Day> {
    public static final Chronology DEFAULT_CHRONOLOGY = Chronology.ofLocale(Locale.getDefault());


    //@formatter:off
    private static final Set<ChronoUnit> VALID_PRECISIONS = Set.of(ChronoUnit.FOREVER,ChronoUnit.YEARS,ChronoUnit.MONTHS,ChronoUnit.DAYS);
    //@formatter:on

    private final ChronoLocalDate date;
    private final boolean circa;
    private final ChronoUnit precision;



    /**
     * @param date      date
     *                  Full date is always required, even fields not covered
     *                  by the give precision. Fields more precise than the
     *                  given precision will be masked on display, but will
     *                  still be considered for sorting purposes.
     * @param precision most precise field in date to interpret:
     *                  {@link ChronoUnit#FOREVER} (completely unknown date, all fields ignored)
     *                  {@link ChronoUnit#YEARS} (only year)
     *                  {@link ChronoUnit#MONTHS (year and month)
     *                  {@link ChronoUnit#DAYS}} (year, month, and day)
     */
    public Day(final ChronoLocalDate date, final ChronoUnit precision) {
        this(date, false, precision);
    }

    private Day(final ChronoLocalDate date, final boolean circa, final ChronoUnit precision) {
        this.date = Objects.requireNonNull(date, "date cannot be null");
        this.circa = circa;
        this.precision = Objects.requireNonNull(precision, "precision cannot be null");
        if (!VALID_PRECISIONS.contains(this.precision)) {
            throw new DateTimeException("Invalid precision");
        }
    }



    /**
     * Creates a "circa" version of this date.
     * But, if this date is unknown, simply return a
     * new copy of this date (because "circa unknown"
     * doesn't make sense).
     *
     * @return circa date
     */
    public Day withCirca() {
        return new Day(this.date, !isUnknown(), this.precision);
    }

    /**
     * Factory method for year in ISO-8601 calendar.
     * The underlying full date (for sorting purposes) will be mid-year (July 2).
     *
     * @param prolepticYear year (proleptic, as defined by ISO=8601)
     * @return new Day, with {@link ChronoUnit#YEARS} precision
     */
    public static Day ofYearIso(final int prolepticYear) {
        return new Day(IsoChronology.INSTANCE.dateYearDay(prolepticYear, 366 / 2), false, ChronoUnit.YEARS);
    }

    /**
     * Factory method for month in ISO-8601 calendar.
     * The underlying full date (for sorting purposes) will be mid-month (the 15th).
     *
     * @param prolepticYear year (proleptic, as defined by ISO=8601)
     * @param month         month (as defined by ISO=8601)
     * @return new Day, with {@link ChronoUnit#MONTHS} precision
     */
    public static Day ofMonthIso(final int prolepticYear, final int month) {
        return new Day(IsoChronology.INSTANCE.date(prolepticYear, month, 30 / 2), false, ChronoUnit.MONTHS);
    }

    /**
     * Factory method for full date in ISO-8601 calendar.
     *
     * @param prolepticYear year (proleptic, as defined by ISO=8601)
     * @param month         month (as defined by ISO=8601)
     * @param day           day (as defined by ISO-8601)
     * @return new Day, with {@link ChronoUnit#MONTHS} precision
     */
    public static Day ofIso(final int prolepticYear, final int month, final int day) {
        return new Day(IsoChronology.INSTANCE.date(prolepticYear, month, day), false, ChronoUnit.DAYS);
    }

    /**
     * Factory method for unknown date in ISO-8601 calendar.
     * The full date must still be given, and will be used for sorting purposes.
     * This allows the user flexibility within the "unknown" value-space as
     * to how to sort a particular date. For example, some events, if the date
     * is unknown, may prefer to sort last, as death or burial. Alternatively,
     * birth may prefer to be sorted first.
     *
     * @param prolepticYear year (proleptic, as defined by ISO=8601)
     * @param month         month (as defined by ISO=8601)
     * @param day           day (as defined by ISO-8601)
     * @return new Day, with {@link ChronoUnit#MONTHS} precision
     */
    public static Day unknownIso(final int prolepticYear, final int month, final int day) {
        return new Day(IsoChronology.INSTANCE.date(prolepticYear, month, day), false, ChronoUnit.FOREVER);
    }



    /**
     * Gets the date. Note that fields more precise than
     * this object's precision should never be considered
     * valid, and never be displayed to an end user.
     *
     * @return date
     */
    public ChronoLocalDate getDate() {
        return this.date;
    }

    public ChronoUnit getPrecision() {
        return this.precision;
    }

    public boolean isUnknown() {
        return this.precision.equals(ChronoUnit.FOREVER);
    }

    public boolean isExact() {
        return this.precision.equals(ChronoUnit.DAYS) && !this.circa;
    }

    public boolean isCirca() {
        return this.circa;
    }

    public boolean isOtherEra() {
        return !this.date
            .getEra()
            .equals(this.date
                .getChronology()
                .dateNow()
                .getEra()) && !isUnknown();
    }

    public boolean isOtherChrono() {
        return !this.date
            .getChronology()
            .equals(DEFAULT_CHRONOLOGY);
    }



    @Override
    public String toString() {
        return toStringHelper(this)
            .addValue(getDisplayWithFullDate())
            .toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Day)) {
            return false;
        }
        final Day that = (Day) object;
        return Objects.equals(this.date, that.date) && Objects.equals(this.circa, that.circa) && Objects.equals(this.precision, that.precision);
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

    @Override
    public int compareTo(final Day that) {
        return Comparator
            .comparing(Day::getDate, ChronoLocalDate.timeLineOrder())
            .compare(this, that);
    }



    public String getDisplay() {
        return formatter().format(this.date);
    }

    public String getDisplayWithFullDate() {
        //@formatter:off
        return
            "Day[" +
            (this.circa ? "c. " : "") +
            DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(this.date) +
            "; p=" +
            this.precision +
            "]";
        //@formatter:on
    }



    private static final int MAX_FIELD_WIDTH = 19;
    private static final int MIN_YEAR_FIELD_WIDTH = 4;
    private static final int MIN_MONTH_FIELD_WIDTH = 2;
    private static final int MIN_DAY_FIELD_WIDTH = 2;
    private static final char FIELD_SEPARATOR = '\u002d';
    private static final char ERA_FIELD_SEPARATOR = '\u0020';
    private static final char CIRCA_INDICATOR = '\u007e';
    private static final char UNKNOWN_INDICATOR = '\u0058';

    private DateTimeFormatter formatter() {
        return bCal(bCirca(bDay(bMonth(bYear(bEra(new DateTimeFormatterBuilder())))))).toFormatter();
    }

    private DateTimeFormatterBuilder bEra(DateTimeFormatterBuilder b) {
        if (isOtherEra()) {
            b = b
                .appendText(ChronoField.ERA, TextStyle.SHORT)
                .appendLiteral(ERA_FIELD_SEPARATOR);
        }
        return b;
    }

    private DateTimeFormatterBuilder bYear(DateTimeFormatterBuilder b) {
        if (asPreciseAs(ChronoUnit.YEARS)) {
            b = b.appendValue(ChronoField.YEAR_OF_ERA, MIN_YEAR_FIELD_WIDTH, MAX_FIELD_WIDTH, SignStyle.NORMAL);
        } else {
            b = b.appendLiteral(unknownField(MIN_YEAR_FIELD_WIDTH));
        }
        b = b.appendLiteral(FIELD_SEPARATOR);
        return b;
    }

    private DateTimeFormatterBuilder bMonth(DateTimeFormatterBuilder b) {
        if (asPreciseAs(ChronoUnit.MONTHS)) {
            b = b.appendValue(ChronoField.MONTH_OF_YEAR, MIN_MONTH_FIELD_WIDTH, MAX_FIELD_WIDTH, SignStyle.NORMAL);
        } else {
            b = b.appendLiteral(unknownField(MIN_MONTH_FIELD_WIDTH));
        }
        b = b.appendLiteral(FIELD_SEPARATOR);
        return b;
    }

    private DateTimeFormatterBuilder bDay(DateTimeFormatterBuilder b) {
        if (asPreciseAs(ChronoUnit.DAYS)) {
            b = b.appendValue(ChronoField.DAY_OF_MONTH, MIN_DAY_FIELD_WIDTH, MAX_FIELD_WIDTH, SignStyle.NORMAL);
        } else {
            b = b.appendLiteral(unknownField(MIN_DAY_FIELD_WIDTH));
        }
        return b;
    }

    private DateTimeFormatterBuilder bCirca(DateTimeFormatterBuilder b) {
        if (isCirca()) {
            b = b.appendLiteral(CIRCA_INDICATOR);
        }
        return b;
    }

    private DateTimeFormatterBuilder bCal(DateTimeFormatterBuilder b) {
        if (isOtherChrono()) {
            b = b.appendLiteral(" (" +
                this.date
                    .getChronology()
                    .getId() +
                ")");
        }
        return b;
    }

    private boolean asPreciseAs(final ChronoUnit u) {
        return comparing(ChronoUnit::getDuration).compare(this.precision, u) <= 0;
    }

    private static String unknownField(final int width) {
        return Strings.repeat(Character.toString(UNKNOWN_INDICATOR), width);
    }
}
