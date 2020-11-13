package nu.mine.mosher.genealdb.model.entity.extract;


import nu.mine.mosher.genealdb.model.entity.conclude.Is;
import nu.mine.mosher.genealdb.model.entity.source.PersonaCites;
import nu.mine.mosher.graph.datawebapp.util.GraphEntity;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
import java.util.*;


@NodeEntity(label=Persona.TYPE)
public class Persona extends GraphEntity implements Serializable {
    public static final String TYPE = "Persona";

    @Property public String name;
    @Property public String description;
    @Property public String notes;

    @Relationship(type=Is.TYPE, direction=Relationship.INCOMING) public Set<Is> xrefs = new HashSet<>();
    @Relationship(type=Role.TYPE) public Set<Role> hadRolesIn = new HashSet<>();
    @Relationship(type= PersonaCites.TYPE) public Set<PersonaCites> cites;

//    @SuppressWarnings("unused")
//    private Long id;
//
//
//
//    @SuppressWarnings("unused")
//    public Persona() {
//    }
//
//    public Persona(final Citation cites, final String description, final String notes) {
//        this.cites = Objects.requireNonNull(cites);
//        this.cites.addLink(this);
//        this.description = Objects.requireNonNull(description);
//        this.notes = Objects.requireNonNull(notes);
//    }
//
//    public void addLink(final Is is) {
//        this.xrefs.add(Objects.requireNonNull(is));
//    }
//
//    void addLink(final Role role) {
//        this.hadRolesIn.add(Objects.requireNonNull(role));
//    }



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
//    public Set<Is> getXrefs() {
//        return unmodifiableSet(this.xrefs);
//    }
//
//    public Set<Role> getHadRolesIn() {
//        return unmodifiableSet(this.hadRolesIn);
//    }
//
//    public Citation getCites() {
//        return this.cites;
//    }
//
//    public String getDescription() {
//        return this.description;
//    }
//
//    public String getNotes() {
//        return this.notes;
//    }
}
