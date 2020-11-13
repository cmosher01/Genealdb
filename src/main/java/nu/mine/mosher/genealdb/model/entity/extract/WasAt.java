package nu.mine.mosher.genealdb.model.entity.extract;

import nu.mine.mosher.genealdb.model.entity.place.Place;
import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.convert.CertaintyConverter;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.io.Serializable;

@RelationshipEntity(type= WasAt.TYPE)
public class WasAt extends GraphEntity implements Serializable {
    public static final String TYPE = "WAS_AT";

    @Convert(CertaintyConverter.class) public Certainty certainty;
    @Property public String notes;

    @StartNode public Event event;
    @EndNode public Place happenedAt;

    @Override
    public String toString() {
        return this.certainty.toString();
    }
}
