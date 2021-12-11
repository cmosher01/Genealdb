package nu.mine.mosher.genealogy.prove;

import java.util.*;

public class Persona extends Descriptor<Uncategorized> {
    public List<ExtractedFrom> citations = new ArrayList<>();
    public List<Is> xrefs = new ArrayList<>();
}
