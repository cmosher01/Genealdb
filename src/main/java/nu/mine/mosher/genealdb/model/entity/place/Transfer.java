package nu.mine.mosher.genealdb.model.entity.place;

import org.neo4j.ogm.annotation.Relationship;

import java.util.*;

public class Transfer implements Comparable<Transfer> {
    @Relationship(type = "OF")
    private Set<Place> ofInferior = new HashSet<>();
    @Relationship(type = "FROM")
    private Set<Place> fromSuperior = new HashSet<>();
    @Relationship(type = "TO")
    private Set<Place> toSuperior = new HashSet<>();

    private int year;
    private String notes;

    @SuppressWarnings("unused")
    private Long id;



    @SuppressWarnings("unused")
    public Transfer() {
    }

    public Transfer(final int year) {
        this(year, "");
    }

    public Transfer(final int year, final String notes) {
        this.year = year;
        this.notes = notes;
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

    @Override
    public int compareTo(final Transfer that) {
        return Integer.compare(this.year, that.year);
    }
}
