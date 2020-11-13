package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.genealdb.model.type.Day;
import nu.mine.mosher.genealdb.model.type.convert.DayConverter;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.io.Serializable;
import java.util.*;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity(label=PlaceChange.TYPE)
public class PlaceChange implements Serializable, Comparable<PlaceChange> {
    public static final String TYPE = "PlaceChange";

    @Convert(DayConverter.class) public Day dateHappened;
    @Property public String notes;

    @Relationship(type=Transform.TYPE, direction = INCOMING) public Set<Transform> transforms = new HashSet<>();
    @Relationship(type=Transfer.TYPE, direction = INCOMING) public Set<Transfer> transfers = new HashSet<>();

//    @SuppressWarnings("unused")
//    private Long id;
//
//    @SuppressWarnings("unused")
//    public PlaceChange() {
//    }
//
//    public PlaceChange(final int year, final String notes) {
//        this(Day.ofYearIso(year), notes);
//    }
//
//    public PlaceChange(final Day day, final String notes) {
//        this.dateHappened = Objects.requireNonNull(day);
//        this.notes = Objects.requireNonNull(notes);
//    }
//
//    public void addTransfer(Transfer transfer) {
//        this.transfers.add(transfer);
//    }
//
//    public void addTransform(Transform transform) {
//        this.transforms.add(transform);
//    }

    @Override
    public String toString() {
        return this.dateHappened.toString();
    }

    @Override
    public int compareTo(final PlaceChange that) {
        return this.dateHappened.compareTo(that.dateHappened);
    }

//    public Long getId() {
//        return this.id;
//    }
//
//    public String getNotes() {
//        return this.notes;
//    }
//
//    public Day getDateHappened() {
//        return this.dateHappened;
//    }
//
//    public Set<Transfer> getTransfers() {
//        return Collections.unmodifiableSet(this.transfers);
//    }
//
//    public Set<Transform> getTransforms() {
//        return Collections.unmodifiableSet(this.transforms);
//    }
}
