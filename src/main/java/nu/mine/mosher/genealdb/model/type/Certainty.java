package nu.mine.mosher.genealdb.model.type;

public class Certainty implements Comparable<Certainty> {
    public static final Certainty UNKNOWN = new Certainty(0L);
    public static final Certainty MUST = new Certainty(10L);
    public static final Certainty MUST_NOT = new Certainty(-10L);

    // 0 = uncertain
    // higher values are more certain
    // negative values indicate certainty of denial?
    // -10 <= certainty <= +10
    private final long certainty;



    public Certainty(final long certainty) {
        this.certainty = certainty;
        if (this.certainty < -10L || +10L < this.certainty) {
            throw new IllegalArgumentException("Invalid certainty: "+certainty+"; must be in range [-10,10].");
        }
    }



    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Certainty)) {
            return false;
        }
        final Certainty that = (Certainty)object;
        return this.certainty == that.certainty;
    }

    @Override
    public int hashCode() {
        return (int)this.certainty;
    }

    @Override
    public int compareTo(final Certainty that) {
        return Long.compare(this.certainty, that.certainty);
    }

    @Override
    public String toString() {
        return ""+this.certainty+"*";
    }



    public boolean isAssertion() {
        return this.certainty > 0L;
    }

    public boolean isDenial() {
        return this.certainty < 0L;
    }

    public long getCertainty() {
        return this.certainty;
    }
}
