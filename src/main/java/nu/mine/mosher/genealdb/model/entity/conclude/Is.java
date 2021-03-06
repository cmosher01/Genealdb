package nu.mine.mosher.genealdb.model.entity.conclude;

import nu.mine.mosher.genealdb.model.entity.extract.Persona;
import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.convert.CertaintyConverter;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.io.Serializable;

@RelationshipEntity(type=Is.TYPE)
public class Is extends GraphEntity implements Serializable {
    public static final String TYPE = "IS";

    @Convert(CertaintyConverter.class) public Certainty certainty;
    @Property public String notes;

    @StartNode public Sameness sameness;
    @EndNode public Persona persona;


//    @SuppressWarnings("unused")
//    private Long id;
//
//
//
//    @SuppressWarnings("unused")
//    public Is() {
//    }
//
//    public Is(final Sameness sameness, final Persona persona, final Certainty certainty, final String notes) {
//        this.sameness = Objects.requireNonNull(sameness);
//        this.sameness.addLink(this);
//        this.persona = Objects.requireNonNull(persona);
//        this.persona.addLink(this);
//        this.certainty = Objects.requireNonNull(certainty);
//        this.notes = Objects.requireNonNull(notes);
//    }




    @Override
    public String toString() {
        return this.certainty.toString();
    }



//    public Long getId() {
//        return this.id;
//    }
//
//    public Sameness getSameness() {
//        return this.sameness;
//    }
//
//    public Persona getPersona() {
//        return this.persona;
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
