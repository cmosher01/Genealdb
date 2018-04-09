package nu.mine.mosher.genealdb.model;



import java.util.*;
import org.neo4j.ogm.annotation.Relationship;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Collections.unmodifiableSet;



public class Persona {
    @Relationship(type = "IS", direction = Relationship.INCOMING)
    private Set<Is> xrefs = new HashSet<>();
    @Relationship(type = "HAD_ROLE_IN")
    private Set<Role> hadRoleIn = new HashSet<>();
    private Citation cites;

    // With slashes around surname, GEDCOM style.
    // For identification purposes only, not for assertion of correctness.
    private String name;

    private Long id;



    public Persona() {
    }

    public Persona(final Citation cites, final String name) {
        this.cites = Objects.requireNonNull(cites);
        this.cites.addLink(this);
        this.name = Objects.requireNonNull(name);
    }

    void addLink(final Is is) {
        this.xrefs.add(Objects.requireNonNull(is));
    }

    void addLink(final Role role) {
        this.hadRoleIn.add(Objects.requireNonNull(role));
    }



    @Override
    public String toString() {
        return toStringHelper(this)
            .add("name", this.name)
            .toString();
    }


    public Set<Is> getXrefs() {
        return unmodifiableSet(this.xrefs);
    }

    public Set<Role> getRoles() {
        return unmodifiableSet(this.hadRoleIn);
    }

    public Citation getCites() {
        return this.cites;
    }

    public String getName() {
        return this.name;
    }
}
