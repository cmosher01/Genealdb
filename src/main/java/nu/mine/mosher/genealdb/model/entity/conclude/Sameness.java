package nu.mine.mosher.genealdb.model.entity.conclude;

import nu.mine.mosher.genealdb.model.entity.source.Citation;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Collections.unmodifiableSet;

public class Sameness {
    @Relationship(type = "IS")
    private Set<Is> are = new HashSet<>();
    private Citation cites;

    private String rationale;
    private String notes;

    @SuppressWarnings("unused")
    private Long id;



    @SuppressWarnings("unused")
    public Sameness() {
    }

    public Sameness(final Citation cites, final String rationale, final String notes) {
        this.cites = Objects.requireNonNull(cites);
        this.cites.addLink(this);
        this.rationale = Objects.requireNonNull(rationale);
        this.notes = Objects.requireNonNull(notes);
    }

    void addLink(final Is is) {
        this.are.add(Objects.requireNonNull(is));
    }



    @Override
    public String toString() {
        return toStringHelper(this)
            .omitNullValues()
            .add("rationale", this.rationale)
            .add("notes", this.notes)
            .toString();
    }



    public Long getId() {
        return this.id;
    }

    public Set<Is> getAre() {
        return unmodifiableSet(this.are);
    }

    public Citation getCites() {
        return this.cites;
    }

    public String getRationale() {
        return this.rationale;
    }

    public String getNotes() {
        return this.notes;
    }
}
