package nu.mine.mosher.genealdb.model.entity.conclude;

import nu.mine.mosher.genealdb.model.entity.source.PersonaCites;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
import java.util.*;

@NodeEntity(label=Sameness.TYPE)
public class Sameness extends GraphEntity implements Serializable {
    public static final String TYPE = "Sameness";

    @Property public String rationale;
    @Property public String notes;

    @Relationship(type=Is.TYPE) public Set<Is> are = new HashSet<>();
    @Relationship(type= PersonaCites.TYPE) public Set<PersonaCites> cites;

//    @SuppressWarnings("unused")
//    private Long id;
//
//
//
//    @SuppressWarnings("unused")
//    public Sameness() {
//    }
//
//    public Sameness(final Citation cites, final String rationale, final String notes) {
//        this.cites = Objects.requireNonNull(cites);
//        this.cites.addLink(this);
//        this.rationale = Objects.requireNonNull(rationale);
//        this.notes = Objects.requireNonNull(notes);
//    }
//
//    void addLink(final Is is) {
//        this.are.add(Objects.requireNonNull(is));
//    }



    @Override
    public String toString() {
        return this.rationale;
    }



//    public Long getId() {
//        return this.id;
//    }
//
//    public Set<Is> getAre() {
//        return unmodifiableSet(this.are);
//    }
//
//    public Citation getCites() {
//        return this.cites;
//    }
//
//    public String getRationale() {
//        return this.rationale;
//    }
//
//    public String getNotes() {
//        return this.notes;
//    }
}
