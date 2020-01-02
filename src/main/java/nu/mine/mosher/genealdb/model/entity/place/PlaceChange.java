package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.genealdb.model.type.*;
import nu.mine.mosher.genealdb.model.type.convert.DayConverter;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.*;

import static com.google.common.base.MoreObjects.toStringHelper;
import static org.neo4j.ogm.annotation.Relationship.INCOMING;

public class PlaceChange implements Comparable<PlaceChange> {
    @Relationship(type = "DURING", direction = INCOMING)
    private Set<Transform> transforms = new HashSet<>();
    @Relationship(type = "DURING", direction = INCOMING)
    private Set<Transfer> transfers = new HashSet<>();

    @Convert(DayConverter.class)
    private Day dateHappened;
    private String notes;

    @SuppressWarnings("unused")
    private Long id;

    @SuppressWarnings("unused")
    public PlaceChange() {
    }

    public PlaceChange(final int year, final String notes) {
        this(Day.ofYearIso(year), notes);
    }

    public PlaceChange(final Day day, final String notes) {
        this.dateHappened = Objects.requireNonNull(day);
        this.notes = Objects.requireNonNull(notes);
    }

    public void addTransfer(Transfer transfer) {
        this.transfers.add(transfer);
    }

    public void addTransform(Transform transform) {
        this.transforms.add(transform);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .omitNullValues()
            .add("dateHappened", this.dateHappened)
            .add("notes", this.notes)
            .toString();
    }

    @Override
    public int compareTo(final PlaceChange that) {
        return this.dateHappened.compareTo(that.dateHappened);
    }

    public Long getId() {
        return this.id;
    }

    public String getNotes() {
        return this.notes;
    }

    public Day getDateHappened() {
        return this.dateHappened;
    }

    public Set<Transfer> getTransfers() {
        return Collections.unmodifiableSet(this.transfers);
    }

    public Set<Transform> getTransforms() {
        return Collections.unmodifiableSet(this.transforms);
    }
}
