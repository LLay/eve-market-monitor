package app.world;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 *
 */
public class WorldUpdater {

    private static final String solarSystemsUrl = "https://crest-tq.eveonline.com/solarsystems/";
    private static final String solarSystemTableName = "solar_systems";
    private static final String regionTableName = "regions";
    private static final String dbName = "world";

    private final RethinkDB r = RethinkDB.r;
    private OkHttpClient client = new OkHttpClient();
    private Connection conn = r.connection().hostname("localhost").port(28015).connect();
    private JSONArray items;

    private JSONArray fetchSolarSystemList() throws IOException, ParseException {
        Request request = new Request.Builder()
                .url(solarSystemsUrl)
                .build();

        Response response = client.newCall(request).execute();
        return toJsonArray(response.body().string());
    }

    public void updateWorldData() throws FailedUpdateException {
        updateSolarSystemList();
        // Then add any new solar systems to db
    }

    private void updateSolarSystemList() throws FailedUpdateException {
        prepareDb();

        try {
            this.items = fetchSolarSystemList();
            for (Object system : items) {
                SolarSystem s = SolarSystem.initFromJson(system);
                r.db(dbName).table(solarSystemTableName).insert(s.toRethinkMap()).run(conn);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FailedUpdateException();
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Poop");
            throw new FailedUpdateException();
        }
    }

    private JSONArray toJsonArray(String data) throws ParseException {
        JSONParser jp = new JSONParser();
        JSONObject o = (JSONObject) jp.parse(data);

        int count = Integer.parseInt((String) o.get("totalCount_str"));
        return (JSONArray) o.get("items");
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
    }

}
