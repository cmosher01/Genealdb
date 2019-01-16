package nu.mine.mosher.genealdb.model.entity.place;

import java.net.URI;
import java.util.TreeSet;
import java.util.Set;
import nu.mine.mosher.genealdb.model.type.convert.PointConverter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.postgis.Point;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class Place {
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

    @Convert(PointConverter.class)
    private Point location; // simple lat./long. (redundant/summary of info in GIS)

    private Long id;

    public Place(final String name, final Point location) {
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

    public double distance(final Place that) {
        return this.location.distance(that.location);
    }
}
