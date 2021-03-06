package nu.mine.mosher.genealdb.model.entity.extract;


import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.convert.CertaintyConverter;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.io.Serializable;



@RelationshipEntity(type=Role.TYPE)
public class Role extends GraphEntity implements Serializable {
    public static final String TYPE = "HAD_ROLE_IN";

    @Property public String description;
    @Convert(CertaintyConverter.class) public Certainty certainty;
    @Property public String notes;

    @StartNode public Persona persona;
    @EndNode public Event event;

//    @SuppressWarnings("unused")
//    private Long id;
//
//
//
//    @SuppressWarnings("unused")
//    public Role() {
//    }
//
//    public Role(final Persona persona, final Event event, final String description, final Certainty certainty, final String notes) {
//        this.persona = Objects.requireNonNull(persona);
//        this.persona.addLink(this);
//        this.event = Objects.requireNonNull(event);
//        this.event.addLink(this);
//        this.description = Objects.requireNonNull(description);
//        this.certainty = Objects.requireNonNull(certainty);
//        this.notes = Objects.requireNonNull(notes);
//    }



    @Override
    public String toString() {
        return this.certainty+" "+this.description;
    }



//    public Long getId() {
//        return this.id;
//    }
//
//    public Persona getPersona() {
//        return this.persona;
//    }
//
//    public Event getEvent() {
//        return this.event;
//    }
//
//    public String getDescription() {
//        return this.description;
//    }
//
//    public Certainty getCertainty() {
//        return this.certainty;
//    }
//
//    public String getNotes() {
//        return this.notes;
//    }
}
