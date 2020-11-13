package nu.mine.mosher.genealogy.prove;

import java.util.*;

public record Persona(
    Descriptor<Uncategorized> descriptor,
    String name,
    List<Is> xrefs
)
{
}
