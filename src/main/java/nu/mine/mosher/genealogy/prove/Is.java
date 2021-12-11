package nu.mine.mosher.genealogy.prove;

import nu.mine.mosher.genealdb.model.type.Certainty;

public class Is extends Descriptor<Uncategorized> {
    public Certainty certainty = Certainty.UNKNOWN;

    public Sameness sameness;
    public Persona persona;
}
