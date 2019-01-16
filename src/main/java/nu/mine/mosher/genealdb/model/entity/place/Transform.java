package nu.mine.mosher.genealdb.model.entity.place;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotation.Relationship;

public class Transform implements Comparable<Transform> {
    @Relationship(type = "FROM")
    private Set<Place> from = new HashSet<>();
    @Relationship(type = "TO")
    private Set<Place> to = new HashSet<>();

    private int year;
    private String notes;

    private Long id;

    public Transform(final int year) {
        this(year, "");
    }

    public Transform(final int year, final String notes) {
        this.year = year;
        this.notes = notes;
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

    @Override
    public int compareTo(final Transform that) {
        return Integer.compare(this.year, that.year);
    }
}
