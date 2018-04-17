package nu.mine.mosher.genealdb.model.entity;



import java.util.*;
import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.Day;
import nu.mine.mosher.genealdb.model.type.convert.CertaintyConverter;
import nu.mine.mosher.genealdb.model.type.convert.DayConverter;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Collections.unmodifiableSet;



public class Event implements Comparable<Event> {
    @Relationship(type = "HAD_ROLE_IN", direction = Relationship.INCOMING)
    private Set<Role> players = new HashSet<>();

    @Convert(DayConverter.class)
    private Day happenedOn;
    private String place;
    private String type;
    private String description;
    @Convert(CertaintyConverter.class)
    private Certainty certaintyOfDate;
    @Convert(CertaintyConverter.class)
    private Certainty certaintyOfPlace;
    private String notes;

    private Long id;



    public Event() {
    }

    public Event(
        final Day happenedOn, final String place, final String type, final String description, final Certainty certaintyOfDate, final Certainty certaintyOfPlace, final String notes) {
        this.happenedOn = Objects.requireNonNull(happenedOn);
        this.place = Objects.requireNonNull(place);
        this.type = Objects.requireNonNull(type);
        this.description = Objects.requireNonNull(description);
        this.certaintyOfDate = Objects.requireNonNull(certaintyOfDate);
        this.certaintyOfPlace = Objects.requireNonNull(certaintyOfPlace);
        this.notes = Objects.requireNonNull(notes);
    }

    void addLink(final Role role) {
        this.players.add(Objects.requireNonNull(role));
    }



    @Override
    public int compareTo(final Event that) {
        return this.happenedOn.compareTo(that.happenedOn);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .addValue(getDisplay())
            .toString();
    }



    public Long getId() {
        return this.id;
    }

    public String getDisplay() {
        return this.happenedOn.getDisplay() + "|" + this.place + "|" + this.type + "|" + this.description;
    }

    public Set<Role> getPlayers() {
        return unmodifiableSet(this.players);
    }

    public Day getDay() {
        return this.happenedOn;
    }

    public String getPlace() {
        return this.place;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public Certainty getCertaintyOfDate() {
        return this.certaintyOfDate;
    }

    public Certainty getCertaintyOfPlace() {
        return this.certaintyOfPlace;
    }

    public String getNotes() {
        return this.notes;
    }
}
