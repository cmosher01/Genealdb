package nu.mine.mosher.genealdb.model.entity;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "CONTAINED")
public class Contained {
    @StartNode
    private Place container;
    @EndNode
    private Place containee;

    private boolean entity;

    private Long id;

    public Contained(final Place container, final Place containee) {
        this.container = container;
        this.containee = containee;
        this.entity = true;
    }
}
