package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=From.TYPE)
public class From extends GraphEntity implements Serializable {
    public static final String TYPE = "FROM";

    @Property public String notes;
    @StartNode public Transfer transfer;
    @EndNode public Place superior;
}
