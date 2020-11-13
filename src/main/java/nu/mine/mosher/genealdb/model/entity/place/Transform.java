package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
import java.util.*;

@NodeEntity(label=Transform.TYPE)
public class Transform extends GraphEntity implements Serializable {
    public static final String TYPE = "Transform";

    @Relationship(type=TransformedDuring.TYPE) public Set<TransformedDuring> during = new HashSet<>();
    @Relationship(type=OutOf.TYPE) public Set<OutOf> outOf = new HashSet<>();
    @Relationship(type=InTo.TYPE) public Set<InTo> inTo = new HashSet<>();

//    @SuppressWarnings("unused")
//    private Long id;
//
//    @SuppressWarnings("unused")
//    public Transform() {
//    }
//
//    public Transform from(final Place from) {
//        this.from.add(from);
//        from.addDtor(this);
//        return this;
//    }
//
//    public Transform to(final Place to) {
//        this.to.add(to);
//        to.addCtor(this);
//        return this;
//    }
//
//    public Transform during(final PlaceChange change) {
//        this.during = change;
//        change.addTransform(this);
//        return this;
//    }
//
//
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public Set<Place> getFrom() {
//        return Collections.unmodifiableSet(this.from);
//    }
//
//    public Set<Place> getTo() {
//        return Collections.unmodifiableSet(this.to);
//    }
//
//    public PlaceChange getDuring() {
//        return this.during;
//    }
}
