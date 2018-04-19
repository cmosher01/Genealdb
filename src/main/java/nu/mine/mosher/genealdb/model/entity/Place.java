package nu.mine.mosher.genealdb.model.entity;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class Place {
    @Relationship(type = "BECAME", direction = INCOMING)
    private Set<Became> construction = new HashSet<>();
    @Relationship(type = "BECAME")
    private Set<Became> destruction = new HashSet<>();

    @Relationship(type = "CONTAINED")
    private Set<Contained> contained = new HashSet<>();
    @Relationship(type = "CONTAINED", direction = INCOMING)
    private Set<Contained> containedBy = new HashSet<>();

    private String name;
    private URI region;

    private Long id;

    public Place(final String name) {
        this.name = name;
    }

    public void self(final LocalDate on) {
        final Became became = new Became(on);
        became.from(this);
        became.to(this);
        this.construction.add(became);
        this.destruction.add(became);
    }

    public void became(final Place that, final LocalDate on) {
        final Became became = new Became(on);
        became.from(this);
        became.to(that);
        that.construction.add(became);
        this.destruction.add(became);
    }

    public void in(final Place container) {
        final Contained contained = new Contained(container, this);
        this.containedBy.add(contained);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
