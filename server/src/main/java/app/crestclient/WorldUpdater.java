package app.crestclient;

import app.world.WorldPlaceType;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;
import okhttp3.OkHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;

/**
 *  Responsible for updating the Database with world data from the EVE CREST API.
 */
public class WorldUpdater {

    private static final String solarSystemsUrl = "https://crest-tq.eveonline.com/solarsystems/";
    private static final String solarSystemTableName = "solarsystems";
    private static final String constellationTableName = "constellations";
    private static final String regionTableName = "regions";
    private static final String gateTableName = "stargates";
    private static final String dbName = "world";

    private final RethinkDB r = RethinkDB.r;
    private OkHttpClient client = new OkHttpClient();
    private Connection conn = r.connection().hostname("localhost").port(28015).connect();

    public void updateWorldData() throws FailedUpdateException {
        this.prepareDb();
        CrestWorldClient cwc = new CrestWorldClient();
        this.updatePlaceList(cwc, WorldPlaceType.SolarSystem);
    }

    private void updatePlaceList(CrestWorldClient client, WorldPlaceType type) throws FailedUpdateException {
        ListenableFuture<JSONArray> itemsFuture;
        try {
            itemsFuture = client.getWorldPlaceList(type);
            itemsFuture.addCallback(items ->
                {
                    for (Object place : items) {
                        JSONObject placeJson = (JSONObject) place;
                        placeJson.get("id");
                        r.db(dbName).table(this.getTableName(type)).insert(place).run(conn);
                    }
                }, items -> System.err.print("Could not get items from CREST API"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("N000000");
            throw new FailedUpdateException();
        }
    }

    private void prepareDb() {
        try {
            r.dbCreate(dbName).run(conn);
        } catch (ReqlOpFailedError e) {
            System.err.println("Error. Database already exists? " + dbName);
        }

        try {
            r.db(dbName).tableCreate(solarSystemTableName).run(conn);
        } catch (ReqlOpFailedError e) {
            System.err.println("Error.  Table already exists? " + solarSystemTableName);
        }

        try {
            r.db(dbName).tableCreate(regionTableName).run(conn);
        } catch (ReqlOpFailedError e) {
            System.err.println("Error.  Table already exists? " + regionTableName);
        }
    }

    private String getTableName(WorldPlaceType type) {
        switch(type) {
            case SolarSystem: return solarSystemTableName;
            case Constellation: return constellationTableName;
            case Region: return regionTableName;
            case Gate: return gateTableName;
            default: return null;
        }
    }

}
