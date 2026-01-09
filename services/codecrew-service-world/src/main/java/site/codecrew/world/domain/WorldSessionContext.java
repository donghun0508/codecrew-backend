package site.codecrew.world.domain;

import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.util.function.Tuple2;
import site.codecrew.world.domain.routing.Session;
import site.codecrew.world.domain.routing.SessionRoute;

public final class WorldSessionContext {

    private final long worldId;
    private final String identityHash;
    private final Session session;
    private World world;
    private Player player;
    private SessionRoute sessionRoute;

    private WorldSessionContext(long worldId, String identityHash, WebSocketSession session) {
        this.worldId = worldId;
        this.identityHash = identityHash;
        this.session = new Session(session);
    }

    public static WorldSessionContext of(long worldId, String identityHash, WebSocketSession session) {
        return new WorldSessionContext(worldId, identityHash, session);
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    private void setWorld(World world) {
        this.world = world;
    }

    public long worldId() {
        return worldId;
    }

    public String identityHash() {
        return identityHash;
    }

    public World world() {
        return world;
    }

    public Player player() {
        return player;
    }

    public SessionRoute sessionRoute() {
        return sessionRoute;
    }

    public Session session() {
        return session;
    }

    public String sessionId() {
        return session.id();
    }

    public void validate() {
        this.world.assertEnterable();
        this.player.assertEnterable();
    }
    private boolean metadataLoaded = false;
    private boolean slotOccupied = false;
    private boolean dbSaved = false;

    private boolean routingSaved = false; // 추가

    public void bind(Tuple2<World, Player> tuple) {
        setWorld(tuple.getT1());
        setPlayer(tuple.getT2());
        createSessionRoute();
    }

    public void createSessionRoute() {
        this.sessionRoute = new SessionRoute(player.nodeId(), session);
    }

    public void markMetadataLoaded() {
        this.metadataLoaded = true;
    }

    public boolean isMetadataLoaded() {
        return this.metadataLoaded;
    }

    public void markSlotOccupied() {
        this.slotOccupied = true;
    }

    public boolean isSlotOccupied() {
        return this.slotOccupied;
    }

    public void markDbSaved() {
        this.dbSaved = true;
    }

    public boolean isDbSaved() {
        return this.dbSaved;
    }

    public void markRoutingSaved() {
        this.routingSaved = true;
    }

    public boolean isRoutingSaved() {
        return this.routingSaved;
    }

    @Override
    public String toString() {
        return "Session{" +
            "id='" + identityHash + '\'' +
            ", world=" + world +
            '}';
    }
}
