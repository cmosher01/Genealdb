package nu.mine.mosher.genealogy.prove;

import java.time.*;
import java.util.*;

public record Descriptor<T extends Enum<T>>(
    UUID pk,
    Opaque id,
    long version,
    boolean persisted,
    boolean dirty,

    ZonedDateTime created,
    ZonedDateTime modified,
    ZonedDateTime snapshot,

    Optional<T> category,
    String customCategory,
    String description,
    String notes
)
{
    public Descriptor {
        if (Objects.isNull(snapshot)) {
            snapshot = ZonedDateTime.now(ZoneOffset.UTC);
        }

        if (Objects.isNull(created) && Objects.isNull(modified)) {
            created = modified = snapshot;
        } else if (Objects.isNull(modified)) {
            modified = created;
        } else if (Objects.isNull(created)) {
            created = modified;
        }

        if (Objects.isNull(pk)) {
            pk = UUID.randomUUID();
        }

        if (Objects.isNull(id)) {
            id = Opaque.empty();
        }

        if (Objects.isNull(category)) {
            category = Optional.empty();
        }

        if (Objects.isNull(customCategory)) {
            customCategory = "";
        }

        if (Objects.isNull(description)) {
            description = "";
        }

        if (Objects.isNull(notes)) {
            notes = "";
        }
    }
}
