package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=To.TYPE)
public class To extends GraphEntity implements Serializable {
    public static final String TYPE = "TO";

    @Property public String notes;
    @StartNode public Transfer transfer;
    @EndNode public Place superior;
}
