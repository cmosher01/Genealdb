package nu.mine.mosher.genealdb.model.entity.place;

import org.neo4j.ogm.annotation.Relationship;

import java.util.*;

public class Transform {
    private PlaceChange during;

    @Relationship(type = "FROM")
    private Set<Place> from = new HashSet<>();
    @Relationship(type = "TO")
    private Set<Place> to = new HashSet<>();

    @SuppressWarnings("unused")
    private Long id;

    @SuppressWarnings("unused")
    public Transform() {
    }

    public Transform from(final Place from) {
        this.from.add(from);
        from.addDtor(this);
        return this;
    }

    public Transform to(final Place to) {
        this.to.add(to);
        to.addCtor(this);
        return this;
    }

    public Transform during(final PlaceChange change) {
        this.during = change;
        change.addTransform(this);
        return this;
    }

    public Set<Place> getFrom() {
        return Collections.unmodifiableSet(this.from);
    }

    public Set<Place> getTo() {
        return Collections.unmodifiableSet(this.to);
    }

    public PlaceChange getDuring() {
        return this.during;
    }
}
