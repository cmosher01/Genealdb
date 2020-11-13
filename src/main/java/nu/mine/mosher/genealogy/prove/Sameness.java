package nu.mine.mosher.genealogy.prove;

import java.util.List;

public record Sameness(
    Descriptor<Uncategorized> descriptor,
    List<Is> are
)
{
}
