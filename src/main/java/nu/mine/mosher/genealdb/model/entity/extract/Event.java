package nu.mine.mosher.genealdb.model.entity.extract;


import nu.mine.mosher.genealdb.model.type.*;
import nu.mine.mosher.genealdb.model.type.convert.*;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.io.Serializable;
import java.util.*;



@NodeEntity(label=Event.TYPE)
public class Event extends GraphEntity implements Serializable, Comparable<Event> {
    public static final String TYPE = "Event";

    @Convert(DayConverter.class) public Day dateHappened;
    @Convert(CertaintyConverter.class) public Certainty certaintyDate;
    @Property public String type;
    @Property public String description;
    @Property public String notes;

    @Relationship(type=Role.TYPE, direction=Relationship.INCOMING) public Set<Role> players = new HashSet<>();
    @Relationship(type=WasAt.TYPE) public WasAt place;

//    @SuppressWarnings("unused")
//    private Long id;
//
//
//
//    @SuppressWarnings("unused")
//    public Event() {
//    }
//
//    public Event(
//        final Day dateHappened, final Place place, final String type, final String description, final Certainty certaintyDate, final Certainty certaintyPlace, final String notes) {
//        this.dateHappened = Objects.requireNonNull(dateHappened);
//        this.place = place;
//        this.type=Objects.requireNonNull(type);
//        this.description = Objects.requireNonNull(description);
//        this.certaintyDate = Objects.requireNonNull(certaintyDate);
//        this.certaintyPlace = Objects.requireNonNull(certaintyPlace);
//        this.notes = Objects.requireNonNull(notes);
//    }
//
//    void addLink(final Role role) {
//        this.players.add(Objects.requireNonNull(role));
//    }



    @Override
    public int compareTo(final Event that) {
        return this.dateHappened.compareTo(that.dateHappened);
    }



//    public Long getId() {
//        return this.id;
//    }
//
//    public Set<Role> getPlayers() {
//        return unmodifiableSet(this.players);
//    }
//
//    public Day getDateHappened() {
//        return this.dateHappened;
//    }
//
//    public Certainty getCertaintyDate() {
//        return this.certaintyDate;
//    }
//
//    public Place getPlace() {
//        return this.place;
//    }
//
//    public Certainty getCertaintyPlace() {
//        return this.certaintyPlace;
//    }
//
//    public String getType() {
//        return this.type;
//    }
//
//    public String getDescription() {
//        return this.description;
//    }
//
//    public String getNotes() {
//        return this.notes;
//    }
}
