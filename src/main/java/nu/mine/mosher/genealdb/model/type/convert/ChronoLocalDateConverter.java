package nu.mine.mosher.genealdb.model.type.convert;

import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;

import java.time.chrono.*;
import java.time.temporal.ChronoField;
import java.util.*;

public class ChronoLocalDateConverter implements CompositeAttributeConverter<ChronoLocalDate> {
    @Override
    public Map toGraphProperties(final ChronoLocalDate chronoLocalDate) {
        final long yearProleptic = chronoLocalDate.get(ChronoField.YEAR);
        final long month = chronoLocalDate.get(ChronoField.MONTH_OF_YEAR);
        final long day = chronoLocalDate.get(ChronoField.DAY_OF_MONTH);
        final String chronologyId = chronoLocalDate.getChronology().getId();

        final Map map = new HashMap();
        map.put("year", yearProleptic);
        map.put("month", month);
        map.put("day", day);
        map.put("chronology", chronologyId);
        return map;
    }

    @Override
    public ChronoLocalDate toEntityAttribute(final Map properties) {
        final int yearProleptic = ((Long) properties.get("year")).intValue();
        final int month = ((Long) properties.get("month")).intValue();
        final int day = ((Long) properties.get("day")).intValue();
        final String chronologyId = (String) properties.get("chronology");

        return Chronology.of(chronologyId).date(yearProleptic, month, day);
    }
}
