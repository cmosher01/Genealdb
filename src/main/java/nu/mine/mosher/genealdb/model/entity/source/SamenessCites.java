package nu.mine.mosher.genealdb.model.entity.source;

import nu.mine.mosher.genealdb.model.entity.conclude.Sameness;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=SamenessCites.TYPE)
public class SamenessCites extends GraphEntity implements Serializable {
    public static final String TYPE = "SAMENESS_CITES";

    @Property public String notes;
    @StartNode public Sameness sameness;
    @EndNode public Citation citation;
}
