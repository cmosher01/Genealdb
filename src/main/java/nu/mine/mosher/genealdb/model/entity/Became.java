package nu.mine.mosher.genealdb.model.entity;

import java.time.LocalDate;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "BECAME")
public class Became {
    @StartNode
    private Place prev;
    @EndNode
    private Place next;

    private LocalDate date;

    private Long id;

    public Became(final LocalDate date) {
        this.date = date;
    }

    public void from(final Place from) {
        this.prev = from;
    }

    public void to(final Place to) {
        this.next = to;
    }
}
