package nu.mine.mosher.genealdb.model.entity.place;

import com.google.openlocationcode.OpenLocationCode;
import nu.mine.mosher.genealdb.model.type.convert.OpenLocationCodeConverter;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.net.URI;
import java.util.*;

import static com.google.common.base.MoreObjects.toStringHelper;
import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class Place {
    public static final int CODE_PRECISION_GENEALOGICAL = 12;

    @Relationship(type = "TO", direction = INCOMING)
    private Set<Transform> construction = new HashSet<>();
    @Relationship(type = "FROM", direction = INCOMING)
    private Set<Transform> destruction = new HashSet<>();

    @Relationship(type = "OF", direction = INCOMING)
    private Set<Transfer> superiors = new HashSet<>();
    @Relationship(type = "FROM", direction = INCOMING)
    private Set<Transfer> losses = new HashSet<>();
    @Relationship(type = "TO", direction = INCOMING)
    private Set<Transfer> gains = new HashSet<>();

    private String name;
    private String notes;

    private URI uriRegion; // URL in GIS DB

    @Convert(OpenLocationCodeConverter.class)
    private OpenLocationCode pluscodeVicinity; // (redundant/summary of info in GIS)

    @SuppressWarnings("unused")
    private Long id;



    @SuppressWarnings("unused")
    public Place() {
    }

    public Place(final String name, final OpenLocationCode pluscodeVicinity, final URI uriRegion, final String notes) {
        this.name = Objects.requireNonNull(name);
        this.pluscodeVicinity = pluscodeVicinity;
        this.uriRegion = uriRegion;
        this.notes = Objects.requireNonNull(notes);
    }

    void addCtor(final Transform place) {
        this.construction.add(place);
    }

    void addDtor(final Transform place) {
        this.destruction.add(place);
    }

    void addSuper(final Transfer place) {
        this.superiors.add(place);
    }

    void addLoss(final Transfer place) {
        this.losses.add(place);
    }

    void addGain(final Transfer place) {
        this.gains.add(place);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .omitNullValues()
            .add("name", this.name)
            .add("uriRegion", this.uriRegion)
            .add("pluscodeVicinity", this.pluscodeVicinity)
            .add("notes", this.notes)
            .toString();
    }

    public String getDisplay() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public URI getUriRegion() {
        return this.uriRegion;
    }

    public Set<Transform> getConstruction() {
        return Collections.unmodifiableSet(this.construction);
    }

    public Set<Transform> getDestruction() {
        return Collections.unmodifiableSet(this.destruction);
    }

    public Set<Transfer> getSuperiors() {
        return Collections.unmodifiableSet(this.superiors);
    }

    public Set<Transfer> getGains() {
        return Collections.unmodifiableSet(this.gains);
    }

    public Set<Transfer> getLosses() {
        return Collections.unmodifiableSet(this.losses);
    }

    public String getNotes() {
        return this.notes;
    }

    public OpenLocationCode getPluscodeVicinity() {
        return this.pluscodeVicinity;
    }
}
