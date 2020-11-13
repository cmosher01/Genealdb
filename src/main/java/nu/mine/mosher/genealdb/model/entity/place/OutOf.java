package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=OutOf.TYPE)
public class OutOf extends GraphEntity implements Serializable {
    public static final String TYPE = "OUT_OF";

    @Property public String notes;
    @StartNode public Transform transform;
    @EndNode public Place from;
}
