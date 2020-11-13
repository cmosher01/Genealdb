package nu.mine.mosher.genealogy.prove;

import org.neo4j.driver.*;
import org.neo4j.driver.types.MapAccessor;

import java.net.URI;

import static org.neo4j.driver.internal.types.TypeConstructor.*;

public class Neo4jPlay {
    private static record Rec(String s){}

    private static final URI NEO = URI.create("neo4j://localhost:7687");

    public static void main(final String... args) {
        final var r = new Rec("test");
        System.out.println(r);

        try (final var driver = GraphDatabase.driver(NEO, AuthTokens.basic("neo4j", "neo4j"))) {
            try (final var session = driver.session()) {
                session.readTransaction(tx -> {
                    final var query = new Query("MATCH (v:Person {primaryName:'Fred Astaire'})-[e]->(v2) RETURN v, e, v2, localdatetime() AS ts LIMIT 3");
                    final Result result = tx.run(query);
                    System.out.println("==========================================");
                    result.stream().forEach(record -> {
                        final var s = new StringBuilder(128);

                        final var v = record.get("v");
                        s.append(" v:");
                        dumpValue(v,s);

                        final var e = record.get("e");
                        s.append(" e:");
                        dumpValue(e,s);

                        final var v2 = record.get("v2");
                        s.append("v2:");
                        dumpValue(v2,s);

                        final var ts = record.get("ts");
                        s.append("ts:");
                        dumpValue(ts,s);

                        System.out.println(s.toString());
                        System.out.println("==========================================");
                    });
                    return null;
                });
            }
        }
    }

    private static void dumpValue(final Value v, final StringBuilder s) {
        final var type = v.type();
        s.append(type.name());
        if (NODE.covers(v)) {
            final var node = v.asNode();

            s.append('[');
            s.append(Long.toString(node.id(), 16));
            s.append(']');

            node.labels().forEach(lab -> s.append(':').append(lab));

            dumpProperties(node,s);
        } else if (RELATIONSHIP.covers(v)) {
            var rel = v.asRelationship();
            s.append('[');
            s.append(Long.toString(rel.id(), 16));
            s.append(']');
            s.append(':');
            s.append(rel.type());

            dumpProperties(rel, s);
        } else if (LOCAL_DATE_TIME.covers(v)) {
            var d = v.asLocalDateTime();
            s.append('{');
            s.append(d.toString());
            s.append('}');
        } else if (STRING.covers(v)) {
            s.append('{');
            s.append(v.asString());
            s.append('}');
        } else if (INTEGER.covers(v)) {
            s.append('{');
            s.append(Long.toString(v.asLong(), 10));
            s.append('}');
        } else {
            s.append("???");
        }
    }

    private static void dumpProperties(final MapAccessor map, final StringBuilder s) {
        s.append('{');
        boolean some = false;
        for (final var k : map.keys()) {
            final Value v = map.get(k);
            if (!some) {
                some = true;
            } else {
                s.append(',');
            }
            s.append(k);
            s.append(':');
            dumpValue(v,s);
        }
        s.append('}');
        s.append('\n');
    }
}
