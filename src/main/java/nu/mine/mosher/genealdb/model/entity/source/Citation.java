package nu.mine.mosher.genealdb.model.entity.source;


import nu.mine.mosher.genealdb.model.entity.conclude.Sameness;
import nu.mine.mosher.genealdb.model.entity.extract.Persona;
import nu.mine.mosher.genealdb.model.type.convert.UriConverter;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.io.Serializable;
import java.net.URI;
import java.util.*;


@NodeEntity(label=Citation.TYPE)
public class Citation extends GraphEntity implements Serializable, Comparable<Citation> {
    public static final String TYPE = "Citation";

    @Property public String description;
    @Convert(UriConverter.class) public URI uriReferenceNote;

    @Relationship(type=SamenessCites.TYPE, direction=Relationship.INCOMING) public Set<Sameness> matchings = new HashSet<>();
    @Relationship(type=PersonaCites.TYPE, direction=Relationship.INCOMING) public Set<Persona> personae = new HashSet<>();




    //    @SuppressWarnings("unused")
//    public Citation() {
//    }
//
//    public Citation(final String description, final URI uriReferenceNote) {
//        this.description = Objects.requireNonNull(description);
//        this.uriReferenceNote = uriReferenceNote;
//    }
//
//    public void addLink(final Persona persona) {
//        this.personae.add(Objects.requireNonNull(persona));
//    }
//
//    public void addLink(final Sameness sameness) {
//        this.matchings.add(Objects.requireNonNull(sameness));
//    }



    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Citation)) {
            return false;
        }
        final Citation that = (Citation) object;
        if (this.uriReferenceNote != null) {
            return this.uriReferenceNote.equals(that.uriReferenceNote);
        }
        return !this.description.isEmpty() && this.description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uriReferenceNote, this.description);
    }

    @Override
    public int compareTo(final Citation that) {
        return this.description.compareTo(that.description);
    }

    @Override
    public String toString() {
        return this.description;
    }

//    public String getDisplay() {
//        return this.description;
//    }
//
//
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public Set<Sameness> getMatchings() {
//        return unmodifiableSet(this.matchings);
//    }
//
//    public Set<Persona> getPersonae() {
//        return unmodifiableSet(this.personae);
//    }
//
//    public String getDescription() {
//        return this.description;
//    }
//
//    public URI getUriReferenceNote() {
//        return this.uriReferenceNote;
//    }
}
