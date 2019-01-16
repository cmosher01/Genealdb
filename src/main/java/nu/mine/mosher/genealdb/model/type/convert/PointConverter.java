package nu.mine.mosher.genealdb.model.type.convert;

import java.util.Collections;
import java.util.Map;
import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;
import org.postgis.Point;

import static java.util.Objects.isNull;

public class PointConverter implements CompositeAttributeConverter<Point> {
    @Override
    public Map<String, ?> toGraphProperties(final Point value) {
        return Map.of("longitude", value.getX(), "latitude", value.getY());
    }

    @Override
    public Point toEntityAttribute(final Map<String, ?> value) {
        return new Point((Double)value.get("longitude"), (Double)value.get("latitude"));
    }
}
