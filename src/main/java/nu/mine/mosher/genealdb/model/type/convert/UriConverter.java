package nu.mine.mosher.genealdb.model.type.convert;

import java.net.URI;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class UriConverter implements AttributeConverter<URI, String> {
    @Override
    public String toGraphProperty(final URI uri) {
        return uri.toString();
    }

    @Override
    public URI toEntityAttribute(final String value) {
        return URI.create(value);
    }
}
