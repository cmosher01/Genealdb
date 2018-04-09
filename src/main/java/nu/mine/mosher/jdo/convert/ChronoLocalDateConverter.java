package nu.mine.mosher.jdo.convert;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.util.Map;
import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;

public class ChronoLocalDateConverter implements CompositeAttributeConverter<ChronoLocalDate> {
    @Override
    public Map<String, ?> toGraphProperties(final ChronoLocalDate chronoLocalDate) {
        final long yearProleptic = chronoLocalDate.get(ChronoField.YEAR);
        final long month = chronoLocalDate.get(ChronoField.MONTH_OF_YEAR);
        final long day = chronoLocalDate.get(ChronoField.DAY_OF_MONTH);
        final String chronologyId = chronoLocalDate.getChronology().getId();

        return Map.of("year", yearProleptic, "month", month, "day", day, "chronology", chronologyId);
    }

    @Override
    public ChronoLocalDate toEntityAttribute(final Map<String, ?> properties) {
        final int yearProleptic = ((Long) properties.get("year")).intValue();
        final int month = ((Long) properties.get("month")).intValue();
        final int day = ((Long) properties.get("day")).intValue();
        final String chronologyId = (String) properties.get("chronology");

        return Chronology.of(chronologyId).date(yearProleptic, month, day);
    }
}
