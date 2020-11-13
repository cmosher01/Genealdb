package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=TransferredDuring.TYPE)
public class TransferredDuring extends GraphEntity implements Serializable {
    public static final String TYPE = "TRANSFERRED_DURING";

    @Property public String notes;
    @StartNode public Transfer transfer;
    @EndNode public PlaceChange during;
}
