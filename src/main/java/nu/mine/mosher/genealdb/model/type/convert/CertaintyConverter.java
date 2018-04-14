package nu.mine.mosher.genealdb.model.type.convert;

import nu.mine.mosher.genealdb.model.type.Certainty;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class CertaintyConverter implements AttributeConverter<Certainty, Long> {
    @Override
    public Long toGraphProperty(final Certainty certainty) {
        return certainty.getCertainty();
    }

    @Override
    public Certainty toEntityAttribute(final Long value) {
        return new Certainty(value);
    }
}
