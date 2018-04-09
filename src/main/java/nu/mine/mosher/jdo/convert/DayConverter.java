package nu.mine.mosher.jdo.convert;

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import nu.mine.mosher.genealdb.model.Day;
import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;
import org.neo4j.ogm.typeconversion.EnumStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayConverter implements CompositeAttributeConverter<Day> {
    private static final Logger LOG = LoggerFactory.getLogger(DayConverter.class);

    @Override
    public Map<String, ?> toGraphProperties(final Day day) {
        LOG.trace("Using custom converter for Day({}) => properties", day.getDisplay());
        final Map properties = new HashMap();
        properties.putAll(new ChronoLocalDateConverter().toGraphProperties(day.getDate()));
        properties.put("circa", day.isCirca());
        properties.put("precision", new EnumStringConverter(ChronoUnit.class).toGraphProperty(day.getPrecision()));
        return properties;
    }

    @Override
    public Day toEntityAttribute(final Map<String, ?> properties) {
        final ChronoLocalDate date = new ChronoLocalDateConverter().toEntityAttribute(properties);
        final ChronoUnit precision = (ChronoUnit)new EnumStringConverter(ChronoUnit.class).toEntityAttribute(properties.get("precision").toString());
        Day day = new Day(date, precision);
        if (Boolean.valueOf(properties.get("circa").toString())) {
            day = day.withCirca();
        }
        LOG.trace("Using custom converter for Day({}) <= properties", day.getDisplay());
        return day;
    }
}
