package nu.mine.mosher.genealogy.prove;

import nu.mine.mosher.genealdb.model.type.Certainty;

public record Is(
    Descriptor<Uncategorized> descriptor,
    Certainty certainty
)
{
}
