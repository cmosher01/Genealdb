package nu.mine.mosher.genealdb.model.type.convert;

import com.google.openlocationcode.OpenLocationCode;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.Objects;

public class OpenLocationCodeConverter implements AttributeConverter<OpenLocationCode, String> {
    @Override
    public String toGraphProperty(final OpenLocationCode loc) {
        if (Objects.isNull(loc)) {
            return StringUtils.EMPTY;
        }
        return loc.getCode();
    }

    @Override
    public OpenLocationCode toEntityAttribute(final String value) {
        if (!OpenLocationCode.isValidCode(value)) {
            return null;
        }
        return new OpenLocationCode(value);
    }
}
