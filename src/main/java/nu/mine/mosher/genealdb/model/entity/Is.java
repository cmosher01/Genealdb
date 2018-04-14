package nu.mine.mosher.genealdb.model.entity;

import java.util.Objects;
import nu.mine.mosher.genealdb.model.type.Certainty;
import nu.mine.mosher.genealdb.model.type.convert.CertaintyConverter;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import static com.google.common.base.MoreObjects.toStringHelper;

@RelationshipEntity(type = "IS")
public class Is {
    @StartNode
    private Sameness sameness;
    @EndNode
    private Persona persona;

    @Convert(CertaintyConverter.class)
    private Certainty certainty;
    private String notes;

    private Long id;



    public Is() {
    }

    public Is(final Sameness sameness, final Persona persona, final Certainty certainty, final String notes) {
        this.sameness = Objects.requireNonNull(sameness);
        this.sameness.addLink(this);
        this.persona = Objects.requireNonNull(persona);
        this.persona.addLink(this);
        this.certainty = Objects.requireNonNull(certainty);
        this.notes = Objects.requireNonNull(notes);
    }



    @Override
    public String toString() {
        return toStringHelper(this)
            .add("cert", this.certainty)
            .add("notes", this.notes)
            .toString();
    }



    public String getDisplay() {
        return "is (" + this.certainty + ")";
    }

    public Sameness getSameness() {
        return this.sameness;
    }

    public Persona getPersona() {
        return this.persona;
    }

    public Certainty getCertainty() {
        return this.certainty;
    }

    public String getNotes() {
        return this.notes;
    }
}
