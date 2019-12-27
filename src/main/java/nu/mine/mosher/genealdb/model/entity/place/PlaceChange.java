package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.genealdb.model.type.*;
import nu.mine.mosher.genealdb.model.type.convert.DayConverter;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.*;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

public class PlaceChange implements Comparable<PlaceChange> {
    @Relationship(type = "DURING", direction = INCOMING)
    private Set<Transform> transforms = new HashSet<>();
    @Relationship(type = "DURING", direction = INCOMING)
    private Set<Transfer> transfers = new HashSet<>();

    @Convert(DayConverter.class)
    private Day happenedOn;
    private String notes;

    @SuppressWarnings("unused")
    private Long id;

    @SuppressWarnings("unused")
    public PlaceChange() {
    }

    public PlaceChange(final int year, final String notes) {
        this.happenedOn = Day.ofYearIso(year);
        this.notes = Objects.requireNonNull(notes);
    }

    @Override
    public int compareTo(final PlaceChange that) {
        return this.happenedOn.compareTo(that.happenedOn);
    }

    public Long getId() {
        return this.id;
    }

    public String getNotes() {
        return this.notes;
    }

    public Day getDay() {
        return this.happenedOn;
    }

    public Set<Transfer> getTransfers() {
        return Collections.unmodifiableSet(this.transfers);
    }

    public Set<Transform> getTransforms() {
        return Collections.unmodifiableSet(this.transforms);
    }

    public void addTransfer(Transfer transfer) {
        this.transfers.add(transfer);
    }

    public void addTransform(Transform transform) {
        this.transforms.add(transform);
    }
}
