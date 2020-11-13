package nu.mine.mosher.genealogy.prove;

import nu.mine.mosher.genealdb.model.type.Certainty;

import java.util.*;

public class Test {
    public static void main(final String... args) {
        final var x = new byte[] {1,3,5,7,9,2,4,6,8};
        final var id = new Opaque(x);
        System.out.println(id);

        final var descIs1 = new Descriptor<Uncategorized>(
            null,
            null,
            0,
            false,
            true,
            null,
            null,
            null,
            null,
            null,
            "is1 desc",
            "is1 notes");
        final var is1 = new Is(descIs1, Certainty.MUST);

        final var descSame1 = new Descriptor<Uncategorized>(
            null,
            id,
            0,
            false,
            true,
            null,
            null,
            null,
            null,
            null,
            "same1 desc",
            "same1 notes");

        var same1 = new Sameness(descSame1, List.of(is1));

        System.out.println(same1);
    }
}
