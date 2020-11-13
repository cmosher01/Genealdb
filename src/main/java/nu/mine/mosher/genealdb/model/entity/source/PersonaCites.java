package nu.mine.mosher.genealdb.model.entity.source;

import nu.mine.mosher.genealdb.model.entity.extract.Persona;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type=PersonaCites.TYPE)
public class PersonaCites extends GraphEntity implements Serializable {
    public static final String TYPE = "PERSONA_CITES";

    @Property public String notes;
    @StartNode public Persona persona;
    @EndNode public Citation citation;
}
