package nu.mine.mosher.genealdb.model;



import java.util.Objects;
import nu.mine.mosher.jdo.convert.CertaintyConverter;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import static com.google.common.base.MoreObjects.toStringHelper;



@RelationshipEntity(type = "HAD_ROLE_IN")
public class Role {
    @StartNode
    private Persona persona;
    @EndNode
    private Event event;

    private String role = "";
    @Convert(CertaintyConverter.class)
    private Certainty certainty;
    private String notes;

    private Long id;



    public Role() {
    }

    public Role(final Persona persona, final Event event, final String role, final Certainty certainty, final String notes) {
        this.persona = Objects.requireNonNull(persona);
        this.persona.addLink(this);
        this.event = Objects.requireNonNull(event);
        this.event.addLink(this);
        this.role = Objects.requireNonNull(role);
        this.certainty = Objects.requireNonNull(certainty);
        this.notes = Objects.requireNonNull(notes);
    }



    @Override
    public String toString() {
        return toStringHelper(this)
            .add("role", this.role)
            .add("cert", this.certainty)
            .toString();
    }



    public String getDisplay() {
        return this.role + " (" + this.certainty + ")";
    }

    public Persona getPersona() {
        return this.persona;
    }

    public Event getEvent() {
        return this.event;
    }

    public String getRole() {
        return this.role;
    }

    public Certainty getCertainty() {
        return this.certainty;
    }

    public String getNotes() {
        return this.notes;
    }
}
