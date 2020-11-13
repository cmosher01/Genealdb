package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=InTo.TYPE)
public class InTo extends GraphEntity implements Serializable {
    public static final String TYPE = "IN_TO";

    @Property public String notes;
    @StartNode public Transform transform;
    @EndNode public Place to;
}
