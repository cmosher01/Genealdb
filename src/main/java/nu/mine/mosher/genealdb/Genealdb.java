package nu.mine.mosher.genealdb;

import org.slf4j.*;
import org.slf4j.bridge.SLF4JBridgeHandler;



public class Genealdb {
    private static final Logger LOG;
    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.FINEST);
        LOG = LoggerFactory.getLogger(Genealdb.class);
    }

//    private static final String[] packagesEntity = new String[] {
//        Citation.class.getPackage().getName(),
//        Place.class.getPackage().getName(),
//        Event.class.getPackage().getName(),
//        Sameness.class.getPackage().getName()
//    };
//
//    public static void main(final String... args) throws IOException {
//        if (args.length != 1) {
//            throw new IllegalArgumentException("usage: genealdb neo4j-host");
//        }
//        final URI uriNeo4j = URI.create("bolt://"+args[0]);
//
//        LOG.trace("opening connection to Neo4j at {} using Bolt driver...", args[0]);
//        final Driver driverNeo = new BoltDriver(GraphDatabase.driver(uriNeo4j));
//
//        LOG.trace("creating Neo4j session factory...");
//        final SessionFactory factoryNeo = new SessionFactory(driverNeo, packagesEntity);
//
//        try {
//            serve(factoryNeo);
//        } catch (final Exception e) {
//            factoryNeo.close();
//            throw e;
//        }
//    }
//
//    private static void serve(final SessionFactory factoryNeo) throws IOException {
//        LOG.trace("loading web page template...");
//        final STGroupFile stg = new STGroupFile("page.stg");
//
//        LOG.trace("creating Neo4j session...");
//        final Session neo = factoryNeo.openSession();
//
//        final ObjectRef init = initDataset(neo);
//
//        final NanoHTTPD server = new NanoHTTPD(8080) {};
//
//        server.setHTTPHandler(http -> {
//            final String uri = http.getUri();
//            LOG.info("received request URI: {}", uri);
//
//            final ObjectRef objRef = getObjectRef(http, init);
//
//            @SuppressWarnings("unchecked")
//            final Object object = neo.load(objRef.cls(), objRef.id(), 3);
//            if (Objects.isNull(object)) {
//                return Response.newFixedLengthResponse(Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "page not found");
//            }
//
//            final Expandable view;
//            if (objRef.cls().equals(Citation.class)) {
//                final Citation entity = (Citation)object;
//                view = buildView(entity);
//            } else if (objRef.cls().equals(Persona.class)) {
//                final Persona entity = (Persona)object;
//                view = buildView(entity);
//            } else if (objRef.cls().equals(Place.class)) {
//                final Place entity = (Place)object;
//                view = buildView(entity);
//            } else {
//                view = Expandable.expd(blank());
//            }
//
//            return Response.newFixedLengthResponse(prePage(stg, view));
//        });
//
//        LOG.trace("starting HTTP server...");
//        server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
//
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            factoryNeo.close();
//            server.stop();
//        }));
//    }
//
//    private static ObjectRef initDataset(final Session neo) {
//        LOG.trace("Searching for an arbitrary citation entity...");
//        ObjectRef init = getArbitraryCitation(neo);
//        if (Objects.isNull(init)) {
//            LOG.warn("No citation entity found; will create a sample dataset...");
//            neo.save(Sample.buildEntities());
//            init = getArbitraryCitation(neo);
//        }
//        return init;
//    }
//
//    private static ObjectRef getArbitraryCitation(final Session neo) {
//        try {
//            final Collection<Citation> citations = neo.loadAll(Citation.class, 1);
//            final Citation citDefault = citations.iterator().next();
//            return new ObjectRef(Citation.class, citDefault.getId());
//        } catch (final Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @SuppressWarnings("rawtypes")
//    private static ObjectRef getObjectRef(final IHTTPSession http, final ObjectRef def) {
//        final Class cls;
//        try {
//            final List<String> arg = http.getParameters().get("entity");
//            cls = Class.forName(arg.get(0));
//        } catch (final Throwable ignore) {
//            return def;
//        }
//
//        final Long i;
//        try {
//            final List<String> arg = http.getParameters().get("id");
//            i = Long.valueOf(arg.get(0));
//        } catch (final Throwable ignore) {
//            return def;
//        }
//
//        return new ObjectRef(cls,i);
//    }
//
//    private static Expandable buildView(final Persona persona) {
//        final List<Expandable> re = persona.getHadRolesIn().stream()
//            .map(Role::getEvent)
//            .distinct()
//            .sorted()
//            .map(Genealdb::getEventDisplay)
//            .collect(Collectors.toList());
//
//        final List<Expandable> rx = persona.getXrefs().stream()
//            .map(is ->
//                expd(line(is.getSameness().getRationale()).withLabel(is.getSameness().getCites()),
//                    getXrefDisplay(is.getSameness())))
//            .collect(toList());
//
//        return expd(blank().withLabel("persona"),
//            expd(line(persona.getDescription()).withLabel("description")),
//            expd(line(persona.getNotes()).withLabel("notes")),
//            expd(line(persona.getCites()).withLabel("citation")),
//            expd(blank().withLabel("extracted events"), re),
//            expd(blank().withLabel("cross references"), rx));
//    }
//
//    private static Expandable buildView(final Citation citation) {
//        final List<Expandable> rp = citation.getPersonae()
//            .stream()
//            .map(Genealdb::getPersonaDisplay)
//            .collect(toList());
//
//        final List<Expandable> re = citation.getPersonae().stream()
//            .flatMap(persona -> persona.getHadRolesIn().stream())
//            .map(Role::getEvent)
//            .distinct()
//            .sorted()
//            .map(Genealdb::getEventDisplay)
//            .collect(toList());
//
//        final List<Expandable> rx = citation.getMatchings().stream()
//            .map(sameness ->
//                expd(line(sameness.getRationale()),
//                    getXrefDisplay(sameness)))
//            .collect(toList());
//
//        return expd(blank().withLabel("citation"),
//            expd(line(citation.getDescription()).withLabel("description")),
//            expd(line(citation.getUriReferenceNote()).withLabel("reference note")),
//            expd(blank().withLabel("extractions by persona"), rp),
//            expd(blank().withLabel("extractions by event"), re),
//            expd(blank().withLabel("matchings"), rx));
//    }
//
//    private static Expandable buildView(final Place place) {
//        final SortedMap<PlaceChange, List<Expandable>> pc = new TreeMap<>();
//        place.getConstruction().forEach(c -> cons(c, pc));
//        place.getDestruction().forEach(c -> destr(c, pc));
//        place.getSuperiors().forEach(s -> supers(s, pc));
//        place.getGains().forEach(s -> gains(s, pc));
//        place.getLosses().forEach(s -> losses(s, pc));
//
//        final List<Expandable> pcs = new ArrayList<>();
//        pc.forEach((key, value) -> pcs.add(expd(getPlaceChangeDisplay(key).withLabel("during"), value)));
//
//        return expd(blank().withLabel("place"),
//            expd(line(place.getName()).withLabel("name")),
//            expd(line(place.getNotes()).withLabel("notes")),
//            expd(line(getPluscodeDisplay(place.getPluscodeVicinity())).withLabel("approximate vicinity")),
//            expd(line(place.getUriRegion()).withLabel("region")),
//            expd(blank().withLabel("transfers and transforms"), pcs));
//    }
//
//    private static class PlusLink {
//        private final URI uri;
//
//        public PlusLink(final String pluscode) {
//            this.uri = URI.create("https://plus.codes/"+pluscode);
//        }
//
//        public String getHost() {
//            return "";
//        }
//
//        public String getDisplay() {
//            return uri.toString();
//        }
//
//        @Override
//        public String toString() {
//            return this.uri.toString();
//        }
//    }
//
//    private static PlusLink getPluscodeDisplay(final OpenLocationCode pluscodeVicinity) {
//        return new PlusLink(pluscodeVicinity.getCode());
//    }
//
//    private static final PlaceChange DURING_UNKOWN = new PlaceChange(Day.unknownIso(1,1,1), "[unknown]");
//
//    private static PlaceChange during(PlaceChange during) {
//        return Objects.isNull(during) ? DURING_UNKOWN : during;
//    }
//
//    private static void cons(Transform c, SortedMap<PlaceChange, List<Expandable>> pc) {
//        List<Expandable> others = c.getFrom().stream().map(t -> expd(line(t).withLabel("transformed from"))).collect(toList());
//        if (others.isEmpty()) {
//            others = new ArrayList<>();
//            others.add(expd(blank().withLabel("was created")));
//        }
//        pc.computeIfAbsent(during(c.getDuring()), k -> new ArrayList<>());
//        pc.get(during(c.getDuring())).addAll(others);
//    }
//
//    private static void destr(Transform c, SortedMap<PlaceChange, List<Expandable>> pc) {
//        List<Expandable> others = c.getTo().stream().map(t -> expd(line(t).withLabel("transformed to"))).collect(toList());
//        if (others.isEmpty()) {
//            others = new ArrayList<>();
//            others.add(expd(blank().withLabel("was destroyed")));
//        }
//        pc.computeIfAbsent(during(c.getDuring()), k -> new ArrayList<>());
//        pc.get(during(c.getDuring())).addAll(others);
//    }
//
//    private static void supers(Transfer c, SortedMap<PlaceChange, List<Expandable>> pc) {
//        List<Expandable> others = c.getFromSuperior().stream().map(t -> expd(line(t).withLabel("was transferred from superior"))).collect(toList());
//        pc.computeIfAbsent(during(c.getDuring()), k -> new ArrayList<>());
//        pc.get(during(c.getDuring())).addAll(others);
//
//        others = c.getToSuperior().stream().map(t -> expd(line(t).withLabel("was transferred to superior"))).collect(toList());
//        pc.computeIfAbsent(during(c.getDuring()), k -> new ArrayList<>());
//        pc.get(during(c.getDuring())).addAll(others);
//    }
//
//    private static void gains(Transfer c, SortedMap<PlaceChange, List<Expandable>> pc) {
//        List<Expandable> others = c.getOfInferior().stream().map(t -> expd(line(t).withLabel("gained as inferior"))).collect(toList());
//        pc.computeIfAbsent(during(c.getDuring()), k -> new ArrayList<>());
//        pc.get(during(c.getDuring())).addAll(others);
//    }
//
//    private static void losses(Transfer c, SortedMap<PlaceChange, List<Expandable>> pc) {
//        List<Expandable> others = c.getOfInferior().stream().map(t -> expd(line(t).withLabel("lost inferior"))).collect(toList());
//        pc.computeIfAbsent(c.getDuring(), k -> new ArrayList<>());
//        pc.get(c.getDuring()).addAll(others);
//    }
//
//    private static Line getPlaceChangeDisplay(final PlaceChange pc) {
//        return line(pc.getDateHappened().getDisplay(), pc.getNotes());
//    }
//
//    private static List<Expandable> getXrefDisplay(final Sameness sameness) {
//        return sameness.getAre().stream()
//            .map(is -> expd(line(
//                "("+is.getCertainty()+")",
//                is.getPersona(),
//                "in", is.getPersona().getCites(),
//                is.getNotes())))
//            .collect(toList());
//    }
//
//    private static Expandable getPersonaDisplay(Persona persona) {
//        return expd(line(persona).withLabel("persona"),
//            persona
//                .getHadRolesIn()
//                .stream()
//                .sorted(Genealdb::byEvent)
//                .map(role -> expd(getEventLine(role.getEvent()).withLabel("("+role.getCertainty()+") was "+role.getDescription()+" in")))
//                .collect(toList()));
//    }
//
//    private static int byEvent(Role a, Role b) {
//        return a.getEvent().compareTo(b.getEvent());
//    }
//
//    private static Expandable getEventDisplay(final Event event) {
//        return expd(getEventLine(event).withLabel("event"),
//            event.getPlayers().stream()
//                .map(role -> expd(line(role.getPersona()).withLabel("("+role.getCertainty()+") "+role.getDescription())))
//                .collect(toList()));
//    }
//
//    private static Line getEventLine(final Event event) {
//        return line(event.getDateHappened().getDisplay(), event.getPlace(), event.getType(), event.getDescription());
//    }
//
//    private static String prePage(final STGroupFile stg, final Expandable x) {
//        return stg
//            .getInstanceOf("prepage")
//            .add("x", x)
//            .render();
//    }
}
