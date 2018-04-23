package nu.mine.mosher.genealdb.model.entity.place;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class Place {
    @Relationship(type = "TO", direction = INCOMING)
    private Set<Transform> construction = new HashSet<>();
    @Relationship(type = "FROM", direction = INCOMING)
    private Set<Transform> destruction = new HashSet<>();

    @Relationship(type = "OF", direction = INCOMING)
    private Set<Transfer> superiors = new HashSet<>();
    @Relationship(type = "FROM", direction = INCOMING)
    private Set<Transfer> losses = new HashSet<>();
    @Relationship(type = "TO", direction = INCOMING)
    private Set<Transfer> gains = new HashSet<>();

    private String name;
    private URI region;

    private Long id;

    public Place(final String name) {
        this.name = name;
    }

    void addCtor(final Transform place) {
        this.construction.add(place);
    }

    void addDtor(final Transform place) {
        this.destruction.add(place);
    }

    void addSuper(final Transfer place) {
        this.superiors.add(place);
    }

    void addLoss(final Transfer place) {
        this.losses.add(place);
    }

    void addGain(final Transfer place) {
        this.gains.add(place);
    }
}
