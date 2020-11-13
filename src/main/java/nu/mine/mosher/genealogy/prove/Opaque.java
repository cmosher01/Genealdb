package nu.mine.mosher.genealogy.prove;

import java.util.Objects;

public record Opaque(
    byte[] blob
)
{
    public Opaque {
        if (Objects.isNull(blob)) {
            blob = new byte[0];
        }
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder(16);
        s.append("opaque{");
        for (int i = 0; i < Math.min(8, this.blob.length); ++i) {
            s.append(String.format("%02x", this.blob[i]));
        }
        s.append("}");
        return s.toString();
    }

    public static Opaque empty() {
        return new Opaque(null);
    }

    public boolean isEmpty() {
        return this.blob.length == 0;
    }
}
