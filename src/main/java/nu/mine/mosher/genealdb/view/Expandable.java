package nu.mine.mosher.genealdb.view;

import java.util.*;

import static java.util.Collections.*;

@SuppressWarnings({"rawtypes", "unused"})
public final class Expandable extends AbstractList {
    private final Line line;
    private final List<Expandable> items;

    private Expandable(final Line line, final List<Expandable> items) {
        this.line = Objects.requireNonNull(line);
        this.items = unmodifiableList(new ArrayList<>(items));
    }



    public static Expandable expd(final Line line) {
        return new Expandable(line, emptyList());
    }

    public static Expandable expd(final Line line, final List<Expandable> items) {
        return new Expandable(line, items);
    }

    public static Expandable expd(final Line line, final Expandable... items) {
        return new Expandable(line, Arrays.asList(items));
    }

    public Line getLine() {
        return this.line;
    }

    public boolean isExpandable() {
        return !this.items.isEmpty();
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public Expandable get(final int index) {
        return this.items.get(index);
    }
}
