package nu.mine.mosher.genealdb.model.entity.place;

import org.neo4j.ogm.annotation.Relationship;

import java.util.*;

public class Transfer {
    private PlaceChange during;

    @Relationship(type = "OF")
    private Set<Place> ofInferior = new HashSet<>();
    @Relationship(type = "FROM")
    private Set<Place> fromSuperior = new HashSet<>();
    @Relationship(type = "TO")
    private Set<Place> toSuperior = new HashSet<>();

    @SuppressWarnings("unused")
    private Long id;



    @SuppressWarnings("unused")
    public Transfer() {
    }

    public Transfer of(final Place place) {
        this.ofInferior.add(place);
        place.addSuper(this);
        return this;
    }

    public Transfer from(final Place place) {
        this.fromSuperior.add(place);
        place.addLoss(this);
        return this;
    }

    public Transfer to(final Place place) {
        this.toSuperior.add(place);
        place.addGain(this);
        return this;
    }

    public Transfer during(final PlaceChange change) {
        this.during = change;
        change.addTransfer(this);
        return this;
    }

    public Set<Place> getOfInferior() {
        return Collections.unmodifiableSet(this.ofInferior);
    }

    public Set<Place> getFromSuperior() {
        return Collections.unmodifiableSet(this.fromSuperior);
    }

    public Set<Place> getToSuperior() {
        return Collections.unmodifiableSet(this.toSuperior);
    }
}
