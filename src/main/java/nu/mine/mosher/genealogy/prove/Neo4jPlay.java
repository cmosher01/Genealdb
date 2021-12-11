package nu.mine.mosher.genealogy.prove;

import nu.mine.mosher.genealdb.model.type.Certainty;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.internal.value.IntegerValue;
import org.neo4j.driver.types.MapAccessor;
import org.neo4j.driver.types.Path;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.neo4j.driver.internal.types.TypeConstructor.*;

public class Neo4jPlay {
    private static final URI NEO = URI.create("neo4j://localhost:7687");

    public static void save(final Persona obj, final Driver neo) {
        if (obj.version < 0L) {
            throw new IllegalStateException("Cannot persist partial entity (version number less than zero)");
        }

        try (final var session = neo.session()) {
            session.writeTransaction(tx -> {
                final var d = obj;
                final var query = new Query(
                    "MERGE (n:Persona {pk:$pk, version:$version}) " +
                        "ON CREATE SET " +
                        "   n.version=$version+1, " +
                        "   n.created=$created, " +
                        "   n.modified=$modified, " +
                        "   n.category=$category, "+
                        "   n.customCategory=$customCategory, "+
                        "   n.description=$description, " +
                        "   n.notes=$notes " +
                        "ON MATCH SET " +
                        "   n.version=$version+1, " +
                        "   n.modified=$modified, " +
                        "   n.category=$category, "+
                        "   n.customCategory=$customCategory, "+
                        "   n.description=$description, " +
                        "   n.notes=$notes " +
                        "RETURN n",
                    Values.parameters(
                        "pk", d.pk.toString(),
                        "version", d.version,
                        "created", d.created,
                        "modified", d.modified,
                        "category", d.category.isPresent() ? d.category.get().name() : null,
                        "customCategory", strprop(d.customCategory),
                        "description", strprop(d.description),
                        "notes", strprop(d.notes)
                    ));
                final var result = tx.run(query);

                return result.consume();
            });
        }

        try (final var session = neo.session()) {
            session.readTransaction(tx -> {
                final var query = new Query(
                    "MATCH (n:Persona {pk:$pk}) RETURN n.version AS version, ID(n) AS ID, datetime({timezone:'Z'}) AS ts",
                    Values.parameters("pk", obj.pk.toString()));

                final var result = tx.run(query);
                final var record = result.single();

                obj.id = new Opaque(toOpaque(record.get("ID")));
                obj.version = record.get("version").asLong();
                obj.snapshot = record.get("ts").asZonedDateTime();
                obj.persisted = true;
                obj.dirty = false;

                return null;
            });
        }
    }

    public static void save(final Sameness obj, final Driver neo) {
        if (obj.version < 0L) {
            throw new IllegalStateException("Cannot persist partial entity (version number less than zero)");
        }

        try (final var session = Objects.requireNonNull(neo).session()) {
            session.writeTransaction(tx -> {
                final var d = obj;
                final var query = new Query(
                "MERGE (n:Sameness {pk:$pk, version:$version}) " +
                    "ON CREATE SET " +
                    "   n.version=$version+1, " +
                    "   n.created=$created, " +
                    "   n.modified=$modified, " +
                    "   n.category=$category, "+
                    "   n.customCategory=$customCategory, "+
                    "   n.description=$description, " +
                    "   n.notes=$notes " +
                    "ON MATCH SET " +
                    "   n.version=$version+1, " +
                    "   n.modified=$modified, " +
                    "   n.category=$category, "+
                    "   n.customCategory=$customCategory, "+
                    "   n.description=$description, " +
                    "   n.notes=$notes " +
                    "RETURN n",
                    Values.parameters(
                        "pk", d.pk.toString(),
                        "version", d.version,
                        "created", d.created,
                        "modified", d.modified,
                        "category", d.category.isPresent() ? d.category.get().name() : null,
                        "customCategory", strprop(d.customCategory),
                        "description", strprop(d.description),
                        "notes", strprop(d.notes)
                    ));
                final var result = tx.run(query);

                return result.consume();
            });
        }

        try (final var session = neo.session()) {
            session.readTransaction(tx -> {
                final var query = new Query(
                    "MATCH (n:Sameness {pk:$pk}) RETURN n.version AS version, ID(n) AS ID, datetime({timezone:'Z'}) AS ts",
                    Values.parameters("pk", obj.pk.toString()));

                final var result = tx.run(query);
                final var record = result.single();

                obj.id = new Opaque(toOpaque(record.get("ID")));
                obj.version = record.get("version").asLong();
                obj.snapshot = record.get("ts").asZonedDateTime();
                obj.persisted = true;
                obj.dirty = false;

                return null;
            });
        }
    }

    private static void save(Is obj, Driver neo) {
        if (obj.version < 0) {
            throw new IllegalStateException("Cannot persist partial entity (version number less than zero)");
        }

        if (!(obj.persona.persisted && obj.sameness.persisted)) {
            throw new IllegalStateException("Cannot persist relationship with any non-persisted nodes");
        }

        try (final var session = Objects.requireNonNull(neo).session()) {
            session.writeTransaction(tx -> {
                final var d = obj;
                final var query = new Query(
                    "MATCH (n_from:Sameness {pk:$pk_from}), (n_to:Persona {pk:$pk_to}) " +
                        "MERGE (n_from)-[r:IS {pk:$pk}]->(n_to) " +
                        "ON CREATE SET " +
                        "   r.version=$version+1, " +
                        "   r.created=$created, " +
                        "   r.modified=$modified, " +
                        "   r.category=$category, "+
                        "   r.customCategory=$customCategory, "+
                        "   r.description=$description, " +
                        "   r.notes=$notes, " +
                        "   r.certainty=$certainty " +
                        "ON MATCH SET " +
                        "   r.version=$version+1, " +
                        "   r.modified=$modified, " +
                        "   r.category=$category, "+
                        "   r.customCategory=$customCategory, "+
                        "   r.description=$description, " +
                        "   r.notes=$notes, " +
                        "   r.certainty=$certainty " +
                        "RETURN r",
                    Values.parameters(
                        "pk_from", obj.sameness.pk.toString(),
                        "pk_to", obj.persona.pk.toString(),
                        "pk", d.pk.toString(),
                        "version", d.version,
                        "created", d.created,
                        "modified", d.modified,
                        "category", d.category.isPresent() ? d.category.get().name() : null,
                        "customCategory", strprop(d.customCategory),
                        "description", strprop(d.description),
                        "notes", strprop(d.notes),
                        "certainty", obj.certainty.getCertainty()
                    ));
                final var result = tx.run(query);

                return result.consume();
            });
        }

        try (final var session = neo.session()) {
            session.readTransaction(tx -> {
                final var query = new Query("MATCH (:Sameness)-[r:IS {pk:$pk}]->(:Persona) RETURN r.version AS version, ID(r) AS ID, datetime({timezone:'Z'}) AS ts",
                    Values.parameters("pk", obj.pk.toString()));

                final var result = tx.run(query);
                final var record = result.single();

                obj.id = new Opaque(toOpaque(record.get("ID")));
                obj.version = record.get("version").asLong();
                obj.snapshot = record.get("ts").asZonedDateTime();
                obj.persisted = true;
                obj.dirty = false;

                return null;
            });
        }
    }

    private static ZonedDateTime ts(final Driver neo) {
        try (final var session = neo.session()) {
            return session.readTransaction(tx -> {
                final var query = new Query("RETURN datetime({timezone:'Z'}) AS ts");
                final var result = tx.run(query);
                final var record = result.single();
                return record.get("ts").asZonedDateTime();
            });
        }
    }

    public static void play(final Driver neo) {
        try (final var session = neo.session()) {
            session.readTransaction(tx -> {
                final var query = new Query("MATCH p = (:Node)-[*1..5]->(:Node) RETURN p");
                final var result = tx.run(query);
                while (result.hasNext()) {
                    final StringBuilder s = new StringBuilder();
                    final Record rec = result.next();
                    rec.values().forEach(v -> dumpValue(v, s));
                    System.out.println(s.toString());
                }
                return null;
            });
        }
    }

    public static void main(final String... args) {
        try (final var driver = GraphDatabase.driver(NEO, AuthTokens.basic("neo4j", "admin"))) {
            play(driver);

//            final var same1 = new Sameness();
//            same1.created = ts(driver);
//            same1.modified = same1.created;
//            same1.snapshot = same1.created;
//            same1.version = 0;
//            same1.description = "same1 desc";
//            same1.notes = "same1 notes";
//
//            final var pers1 = new Persona();
//            pers1.created = ts(driver);
//            pers1.modified = pers1.created;
//            pers1.snapshot = pers1.created;
//            pers1.version = 0;
//            pers1.description = "pers1 desc";
//            pers1.notes = "pers1 notes";
//
//            final var is1 = new Is();
//            is1.created = ts(driver);
//            is1.modified = is1.created;
//            is1.snapshot = is1.created;
//            is1.sameness = same1;
//            same1.are.add(is1);
//            is1.persona = pers1;
//            pers1.xrefs.add(is1);
//            is1.version = 0;
//            is1.description = "same1 is1 pers1 desc";
//            is1.notes = "same1 is1 pers1 notes";
//
//
//
//            System.out.println(same1);
//            System.out.println(pers1);
//            System.out.println(is1);
//
//            save(same1, driver);
//            save(pers1, driver);
//            save(is1, driver);
//
//            System.out.println(same1);
//            System.out.println(pers1);
//            System.out.println(is1);
        }
    }

    private static String propstr(final Value v) {
        if (v.isNull()) {
            return "";
        }
        final var s = v.asString();
        if (s.isBlank()) {
            return "";
        }
        return s;
    }

    private static byte[] toOpaque(final Value id) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id.asInt()).array();
    }

    private static String strprop(final String s) {
        if (Objects.isNull(s) || s.isBlank()) {
            return null;
        }
        return s;
    }

    private static void dumpValue(final Value v, final StringBuilder s) {
        final var type = v.type();
        s.append(type.name());
        if (PATH.covers(v)) {
            final Path p = v.asPath();
            p.forEach(i -> {
                s.append('/');
                dumpValue(new IntegerValue(i.start().id()), s);
            });
        } else if (NODE.covers(v)) {
            final var node = v.asNode();

            s.append('[');
            s.append(Long.toString(node.id(), 16));
            s.append(']');
            node.labels().forEach(lab -> s.append(':').append(lab));

            dumpProperties(node,s);
        } else if (RELATIONSHIP.covers(v)) {
            final var rel = v.asRelationship();
            s.append('[');
            s.append(Long.toString(rel.id(), 16));
            s.append(']');
            s.append(':');
            s.append(rel.type());

            dumpProperties(rel, s);
        } else if (DATE_TIME.covers(v)) {
            final var d = v.asZonedDateTime();
            s.append('{');
            s.append(d.toString());
            s.append('}');
        } else if (LOCAL_DATE_TIME.covers(v)) {
            final var d = v.asLocalDateTime();
            s.append('{');
            s.append(d.toString());
            s.append('}');
        } else if (INTEGER.covers(v)) {
            s.append('{');
            final var i = v.asLong();
            s.append(Long.toString(i, 10));
            s.append('}');
        } else if (STRING.covers(v)) {
            s.append('{');
            s.append(v.asString());
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
