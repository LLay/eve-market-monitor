package app.world;

import java.security.KeyException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by amindenwebb on 2016-07-07.
 */
public class WorldGroup implements WorldPlace {

    String name;
    String id;

    HashMap<String, SolarSystem> systems;

    public Set<String> getSystems() {
        return this.systems.keySet();
    }

    public SolarSystem getSystem(String id) throws Exception {
        if (this.systems.containsKey(id)) {
            return this.systems.get(id);
        } else {
            throw new NullPointerException("No such solar system: " + id);
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}
