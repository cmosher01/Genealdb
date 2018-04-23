package nu.mine.mosher.genealdb.view;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;

@SuppressWarnings("unchecked")
public class Line {
    /**
     * label, or null
     * typically a String, but could be anything
     */
    public final Object label;

    /**
     * values; always exists, either 0, 1, or more items
     * intended for display on one line (after any label)
     *
     * items will typically be POJOs whose toString is
     * intended for display, and could be heterogeneous
     * these should not be Line or Expandable objects
     */
    public final List values;



    public static Line blank() {
        return new Line(emptyList(), null);
    }

    public static Line line(final Object singletonValue) {
        return new Line(singletonList(singletonValue), null);
    }

    public static Line line(final List values) {
        return new Line(values, null);
    }

    public static Line line(final Object... values) {
        return new Line(Arrays.asList(values), null);
    }

    public Line withLabel(final Object label) {
        return new Line(this.values, label);
    }



    private Line(final List values, final Object label) {
        this.values = safe(values);
        this.label = label;
    }

    private static List safe(final List list) {
        return Objects.isNull(list) ? emptyList() : unmodifiableList(new ArrayList(list));
    }
}
