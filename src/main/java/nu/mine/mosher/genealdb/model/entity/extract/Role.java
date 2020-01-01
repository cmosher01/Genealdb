package nu.mine.mosher.genealdb.model.entity.extract;


import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.convert.CertaintyConverter;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;



@RelationshipEntity(type = "HAD_ROLE_IN")
public class Role {
    @StartNode
    private Persona persona;
    @EndNode
    private Event event;

    private String description;
    @Convert(CertaintyConverter.class)
    private Certainty certainty;
    private String notes;

    @SuppressWarnings("unused")
    private Long id;



    @SuppressWarnings("unused")
    public Role() {
    }

    public Role(final Persona persona, final Event event, final String description, final Certainty certainty, final String notes) {
        this.persona = Objects.requireNonNull(persona);
        this.persona.addLink(this);
        this.event = Objects.requireNonNull(event);
        this.event.addLink(this);
        this.description = Objects.requireNonNull(description);
        this.certainty = Objects.requireNonNull(certainty);
        this.notes = Objects.requireNonNull(notes);
    }



    @Override
    public String toString() {
        return toStringHelper(this)
            .omitNullValues()
            .add("description", this.description)
            .add("certainty", this.certainty)
            .add("notes", this.notes)
            .toString();
    }



    public Long getId() {
        return this.id;
    }

    public Persona getPersona() {
        return this.persona;
    }

    public Event getEvent() {
        return this.event;
    }

    public String getDescription() {
        return this.description;
    }

    public Certainty getCertainty() {
        return this.certainty;
    }

    public String getNotes() {
        return this.notes;
    }
}
