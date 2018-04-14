package nu.mine.mosher.genealdb.model.entity;

import java.util.*;
import org.neo4j.ogm.annotation.Relationship;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Collections.unmodifiableSet;

public class Sameness {
    @Relationship(type = "IS")
    private Set<Is> are = new HashSet<>();
    private Citation cites;

    private String rationale;

    private Long id;



    public Sameness() {
    }

    public Sameness(final Citation cites, final String rationale) {
        this.cites = Objects.requireNonNull(cites);
        this.cites.addLink(this);
        this.rationale = Objects.requireNonNull(rationale);
    }

    void addLink(final Is is) {
        this.are.add(Objects.requireNonNull(is));
    }



    @Override
    public String toString() {
        return toStringHelper(this)
            .add("rationale", this.rationale)
            .toString();
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
}
