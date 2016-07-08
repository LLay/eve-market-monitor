package app.world;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.model.MapObject;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by amindenwebb on 2016-07-07.
 */
public class SolarSystem implements WorldPlace {

    int x;
    int y;
    int z;

    String id;
    String name;

    boolean sparse = true;

    List<String> stations;
    List<String> gates;


    public SolarSystem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setDetails(int x, int y, int z, List<String> stations, List<String> gates) {
        this.stations = stations;
        this.gates = gates;
        this.x = x;
        this.y = y;
        this.z = z;
        this.sparse = false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    public Map toRethinkMap() {
        RethinkDB r = RethinkDB.r;

        MapObject baseMap = r.hashMap("id", id).with("name", name);

        if (!sparse) {
            return baseMap
                    .with("position", Arrays.asList(new int[]{x, y, z}))
                    .with("stations", stations)
                    .with("gates", gates);
        }
        return baseMap;
    }

    public static SolarSystem initFromJson(JSONObject o) {
        String name = (String) o.get("name");
        String id = (String) o.get("id_str");
        return new SolarSystem(id, name);
    }

    public static SolarSystem initFromJson(Object o) {
        if (!(o instanceof JSONObject)) {
            throw new IllegalArgumentException("Object is not a JSONObject");
        } else {
            return initFromJson((JSONObject) o);
        }
    }
}
