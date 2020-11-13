package nu.mine.mosher.genealdb.model.entity.place;

import com.google.openlocationcode.OpenLocationCode;
import nu.mine.mosher.genealdb.model.type.convert.OpenLocationCodeConverter;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.io.Serializable;
import java.net.URI;
import java.util.*;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity(label=Place.TYPE)
public class Place extends GraphEntity implements Serializable {
    public static final String TYPE = "Place";

    @Property private String name;
    @Property private String notes;

    @Property private URI uriRegion; // URL in GIS DB

    // (redundant/summary of info in GIS)
    public static final int CODE_PRECISION_GENEALOGICAL = 12;
    @Convert(OpenLocationCodeConverter.class) public OpenLocationCode pluscodeVicinity;

    @Relationship(type=InTo.TYPE, direction = INCOMING) public Set<InTo> construction = new HashSet<>();
    @Relationship(type=OutOf.TYPE, direction = INCOMING) public Set<OutOf> destruction = new HashSet<>();

    @Relationship(type=Of.TYPE, direction = INCOMING) public Set<Of> superiors = new HashSet<>();
    @Relationship(type=From.TYPE, direction = INCOMING) public Set<From> losses = new HashSet<>();
    @Relationship(type=To.TYPE, direction = INCOMING) public Set<To> gains = new HashSet<>();





//    @SuppressWarnings("unused")
//    private Long id;
//
//
//
//    @SuppressWarnings("unused")
//    public Place() {
//    }
//
//    public Place(final String name, final OpenLocationCode pluscodeVicinity, final URI uriRegion, final String notes) {
//        this.name = Objects.requireNonNull(name);
//        this.pluscodeVicinity = pluscodeVicinity;
//        this.uriRegion = uriRegion;
//        this.notes = Objects.requireNonNull(notes);
//    }
//
//    void addCtor(final Transform place) {
//        this.construction.add(place);
//    }
//
//    void addDtor(final Transform place) {
//        this.destruction.add(place);
//    }
//
//    void addSuper(final Transfer place) {
//        this.superiors.add(place);
//    }
//
//    void addLoss(final Transfer place) {
//        this.losses.add(place);
//    }
//
//    void addGain(final Transfer place) {
//        this.gains.add(place);
//    }

    @Override
    public String toString() {
        return this.name;
    }

//    public String getDisplay() {
//        return this.name;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public URI getUriRegion() {
//        return this.uriRegion;
//    }
//
//    public Set<Transform> getConstruction() {
//        return Collections.unmodifiableSet(this.construction);
//    }
//
//    public Set<Transform> getDestruction() {
//        return Collections.unmodifiableSet(this.destruction);
//    }
//
//    public Set<Transfer> getSuperiors() {
//        return Collections.unmodifiableSet(this.superiors);
//    }
//
//    public Set<Transfer> getGains() {
//        return Collections.unmodifiableSet(this.gains);
//    }
//
//    public Set<Transfer> getLosses() {
//        return Collections.unmodifiableSet(this.losses);
//    }
//
//    public String getNotes() {
//        return this.notes;
//    }
//
//    public OpenLocationCode getPluscodeVicinity() {
//        return this.pluscodeVicinity;
//    }
}
