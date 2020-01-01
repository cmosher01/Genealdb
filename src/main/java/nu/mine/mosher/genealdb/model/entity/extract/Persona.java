package nu.mine.mosher.genealdb.model.entity.extract;


import nu.mine.mosher.genealdb.model.entity.conclude.Is;
import nu.mine.mosher.genealdb.model.entity.source.Citation;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Collections.unmodifiableSet;



public class Persona {
    @Relationship(type = "IS", direction = Relationship.INCOMING)
    private Set<Is> xrefs = new HashSet<>();
    @Relationship(type = "HAD_ROLE_IN")
    private Set<Role> hadRolesIn = new HashSet<>();
    private Citation cites;

    private String description;
    private String notes;

    @SuppressWarnings("unused")
    private Long id;



    @SuppressWarnings("unused")
    public Persona() {
    }

    public Persona(final Citation cites, final String description, final String notes) {
        this.cites = Objects.requireNonNull(cites);
        this.cites.addLink(this);
        this.description = Objects.requireNonNull(description);
        this.notes = Objects.requireNonNull(notes);
    }

    public void addLink(final Is is) {
        this.xrefs.add(Objects.requireNonNull(is));
    }

    void addLink(final Role role) {
        this.hadRolesIn.add(Objects.requireNonNull(role));
    }



    @Override
    public String toString() {
        return toStringHelper(this)
            .omitNullValues()
            .add("description", this.description)
            .add("notes", this.notes)
            .toString();
    }

    public String getDisplay() {
        return this.description;
    }



    public Long getId() {
        return this.id;
    }

    public Set<Is> getXrefs() {
        return unmodifiableSet(this.xrefs);
    }

    public Set<Role> getHadRolesIn() {
        return unmodifiableSet(this.hadRolesIn);
    }

    public Citation getCites() {
        return this.cites;
    }

    public String getDescription() {
        return this.description;
    }

    public String getNotes() {
        return this.notes;
    }
}
