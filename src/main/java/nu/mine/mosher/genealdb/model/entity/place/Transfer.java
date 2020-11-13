package nu.mine.mosher.genealdb.model.entity.place;

import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
import java.util.*;

@NodeEntity(label=Transfer.TYPE)
public class Transfer extends GraphEntity implements Serializable {
    public static final String TYPE = "Transfer";

    @Relationship(type=TransferredDuring.TYPE) public Set<TransferredDuring> during = new HashSet<>();
    @Relationship(type=Of.TYPE) public Set<Of> ofInferior = new HashSet<>();
    @Relationship(type=From.TYPE) public Set<From> fromSuperior = new HashSet<>();
    @Relationship(type=To.TYPE) public Set<To> toSuperior = new HashSet<>();

//    @SuppressWarnings("unused")
//    private Long id;
//
//
//
//    @SuppressWarnings("unused")
//    public Transfer() {
//    }
//
//    public Transfer of(final Place place) {
//        this.ofInferior.add(place);
//        place.addSuper(this);
//        return this;
//    }
//
//    public Transfer from(final Place place) {
//        this.fromSuperior.add(place);
//        place.addLoss(this);
//        return this;
//    }
//
//    public Transfer to(final Place place) {
//        this.toSuperior.add(place);
//        place.addGain(this);
//        return this;
//    }
//
//    public Transfer during(final PlaceChange change) {
//        this.during = change;
//        change.addTransfer(this);
//        return this;
//    }
//
//
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public Set<Place> getOfInferior() {
//        return Collections.unmodifiableSet(this.ofInferior);
//    }
//
//    public Set<Place> getFromSuperior() {
//        return Collections.unmodifiableSet(this.fromSuperior);
//    }
//
//    public Set<Place> getToSuperior() {
//        return Collections.unmodifiableSet(this.toSuperior);
//    }
//
//    public PlaceChange getDuring() {
//        return this.during;
//    }
}
