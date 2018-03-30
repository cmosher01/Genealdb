package nu.mine.mosher.genealdb;

import com.google.common.math.Stats;

import java.time.DateTimeException;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.ValueRange;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Day
{
    private final boolean circa;
    private final Chronology chronology;
    private final Optional<Integer> prolepticYear;
    private final Optional<Integer> month;
    private final Optional<Integer> day;

    private final transient ChronoUnit precision;
    private final transient Optional<ChronoLocalDate> cld;

    public Day(final boolean circa, final Chronology chronology, final Optional<Integer> prolepticYear, final Optional<Integer> month, final Optional<Integer> day)
    {
        this.circa = circa;
        this.chronology = chronology;
        this.prolepticYear = prolepticYear;
        this.month = month;
        this.day = day;

        if (!this.prolepticYear.isPresent()) {
            this.precision = ChronoUnit.FOREVER;
        } else if (!this.month.isPresent()) {
            this.precision = ChronoUnit.YEARS;
        } else if (!this.day.isPresent()) {
            this.precision = ChronoUnit.MONTHS;
        } else {
            this.precision = ChronoUnit.DAYS;
        }

        this.cld = heuristicCld();
    }

    private Optional<ChronoLocalDate> heuristicCld() {
        if (this.precision.equals(ChronoUnit.DAYS)) {
            return Optional.of(this.chronology.date(prolepticYear.get(), month.get(), day.get()));
        }
        if (this.precision.equals(ChronoUnit.MONTHS)) {
            return Optional.of(this.chronology.date(prolepticYear.get(), month.get(), average(this.chronology.range(ChronoField.DAY_OF_MONTH))));
        }
        if (this.precision.equals(ChronoUnit.YEARS)) {
            return Optional.of(this.chronology.dateYearDay(prolepticYear.get(), average(this.chronology.range(ChronoField.DAY_OF_YEAR))));
        }
        return Optional.empty();
    }

    private static int average(final ValueRange range)
    {
        final int a = (int)Math.round(Stats.meanOf(range.getMinimum(), range.getMaximum()));
        if (range.isValidValue(a)) {
            return a;
        }
        if (range.isValidValue(1)) {
            return 1;
        }
        throw new DateTimeException("Cannot determine average value for range.");
    }
}
