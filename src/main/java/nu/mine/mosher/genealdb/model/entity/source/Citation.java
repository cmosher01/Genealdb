package nu.mine.mosher.genealdb.model.entity.source;


import com.google.common.collect.*;
import nu.mine.mosher.genealdb.model.entity.conclude.Sameness;
import nu.mine.mosher.genealdb.model.entity.extract.Persona;
import nu.mine.mosher.genealdb.model.type.convert.UriConverter;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.net.URI;
import java.util.*;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Collections.unmodifiableSet;



public class Citation implements Comparable<Citation> {
    @Relationship(type = "CITES", direction = Relationship.INCOMING)
    private Set<Sameness> matchings = new HashSet<>();
    @Relationship(type = "CITES", direction = Relationship.INCOMING)
    private Set<Persona> personae = new HashSet<>();

    private String description;
    @Convert(UriConverter.class)
    private URI uriReferenceNote;

    @SuppressWarnings("unused")
    private Long id;



    @SuppressWarnings("unused")
    public Citation() {
    }

    public Citation(final String description, final URI uriReferenceNote) {
        this.description = Objects.requireNonNull(description);
        this.uriReferenceNote = uriReferenceNote;
    }

    public void addLink(final Persona persona) {
        this.personae.add(Objects.requireNonNull(persona));
    }

    public void addLink(final Sameness sameness) {
        this.matchings.add(Objects.requireNonNull(sameness));
    }



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
        return ComparisonChain
            .start()
            .compare(
                this.uriReferenceNote,
                that.uriReferenceNote,
                Ordering
                    .natural()
                    .nullsLast())
            .compare(this.description, that.description)
            .result();
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .add("description", this.description)
            .add("uriReferenceNote", this.uriReferenceNote)
            .toString();
    }

    public String getDisplay() {
        return this.description;
    }



    public Long getId() {
        return this.id;
    }

    public Set<Sameness> getMatchings() {
        return unmodifiableSet(this.matchings);
    }

    public Set<Persona> getPersonae() {
        return unmodifiableSet(this.personae);
    }

    public String getDescription() {
        return this.description;
    }

    public URI getUriReferenceNote() {
        return this.uriReferenceNote;
    }
}
