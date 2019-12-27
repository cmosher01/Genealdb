package nu.mine.mosher.genealdb.model.type;

import java.util.Objects;

public class ObjectRef {
    public ObjectRef(Class cls, Long id) {
        this.cls = Objects.requireNonNull(cls);
        this.id = Objects.requireNonNull(id);
    }

    private final Class cls;
    private final Long id;

    public String getCls() {
        return this.cls.getName();
    }

    public String getId() {
        return this.id.toString();
    }

    public Class cls() {
        return this.cls;
    }

    public Long id() {
        return this.id;
    }
}
