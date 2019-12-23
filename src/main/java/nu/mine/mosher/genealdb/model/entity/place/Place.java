package nu.mine.mosher.genealdb.model.entity.place;

import com.google.openlocationcode.OpenLocationCode;
import nu.mine.mosher.genealdb.model.type.convert.OpenLocationCodeConverter;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.net.URI;
import java.util.*;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class Place {
    public static final int CODE_PRECISION_GENEALOGICAL = 12;

    @Relationship(type = "TO", direction = INCOMING)
    private Set<Transform> construction = new TreeSet<>();
    @Relationship(type = "FROM", direction = INCOMING)
    private Set<Transform> destruction = new TreeSet<>();

    @Relationship(type = "OF", direction = INCOMING)
    private Set<Transfer> superiors = new TreeSet<>();
    @Relationship(type = "FROM", direction = INCOMING)
    private Set<Transfer> losses = new TreeSet<>();
    @Relationship(type = "TO", direction = INCOMING)
    private Set<Transfer> gains = new TreeSet<>();

    private String name;

    private URI region; // URL in GIS DB

    @Convert(OpenLocationCodeConverter.class)
    private OpenLocationCode location; // (redundant/summary of info in GIS)

    @SuppressWarnings("unused")
    private Long id;



    @SuppressWarnings("unused")
    public Place() {
    }

    public Place(final String name, final OpenLocationCode location) {
        this.name = name;
        this.location = location;
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

//    public double distance(final Place that) {
//        return this.location.distance(that.location);
//    }


    @Override
    public String toString() {
        return this.name;
    }
}
