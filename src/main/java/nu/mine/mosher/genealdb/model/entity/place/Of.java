package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=Of.TYPE)
public class Of extends GraphEntity implements Serializable {
    public static final String TYPE = "OF";

    @Property public String notes;
    @StartNode public Transfer transfer;
    @EndNode public Place inferior;
}
