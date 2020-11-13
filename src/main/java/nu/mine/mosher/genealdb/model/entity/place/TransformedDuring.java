package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=TransferredDuring.TYPE)
public class TransformedDuring extends GraphEntity implements Serializable {
    public static final String TYPE = "TRANSFORMED_DURING";

    @Property public String notes;
    @StartNode public Transform transform;
    @EndNode public PlaceChange during;
}
