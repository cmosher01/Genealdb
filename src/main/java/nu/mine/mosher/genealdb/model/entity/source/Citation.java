package nu.mine.mosher.genealdb.model.entity.source;



import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.net.URI;
import java.util.*;
import nu.mine.mosher.genealdb.model.entity.extract.Persona;
import nu.mine.mosher.genealdb.model.entity.conclude.Sameness;
import nu.mine.mosher.genealdb.model.type.convert.UriConverter;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Collections.unmodifiableSet;



public class Citation implements Comparable<Citation> {
    @Relationship(type = "CITES", direction = Relationship.INCOMING)
    private Set<Sameness> matchings = new HashSet<>();
    @Relationship(type = "CITES", direction = Relationship.INCOMING)
    private Set<Persona> personae = new HashSet<>();

    private String brief;
    @Convert(UriConverter.class)
    private URI uri;

    private Long id;



    public Citation() {
    }

    public Citation(final String brief, final URI uri) {
        this.brief = Objects.requireNonNull(brief);
        this.uri = uri;
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
        if (this.uri != null) {
            return this.uri.equals(that.uri);
        }
        return !this.brief.isEmpty() && this.brief.equals(that.brief);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uri, this.brief);
    }

    @Override
    public int compareTo(final Citation that) {
        return ComparisonChain
            .start()
            .compare(
                this.uri,
                that.uri,
                Ordering
                    .natural()
                    .nullsLast())
            .compare(this.brief, that.brief)
            .result();
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .add("brief", this.brief)
            .add("uri", this.uri)
            .toString();
    }



    public String getDisplay() {
        String s = this.brief;
        if (Objects.nonNull(this.uri)) {
            s += " ( " + this.uri + " )";
        }
        return s;
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

    public String getBrief() {
        return this.brief;
    }

    public URI getUri() {
        return this.uri;
    }
}