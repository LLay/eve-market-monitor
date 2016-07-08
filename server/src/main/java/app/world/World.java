package app.world;

import java.util.Map;

/**
 * Created by amindenwebb on 2016-07-07.
 */
public class World {

    private static World instance;

    Map<String, SolarSystem> solarSystems;
    Map<String, Region> regions;

    private World() {
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

}
