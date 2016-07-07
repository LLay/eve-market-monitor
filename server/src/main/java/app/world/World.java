package app.world;

import com.rethinkdb.gen.ast.Json;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Set;

/**
 *
 */
public class World {

    Set<SolarSystem> solarSystems;
    Set<Region> regions;

    static final String solarSystemsUrl = "https://crest-tq.eveonline.com/solarsystems/";
    OkHttpClient client = new OkHttpClient();

    private String fetchSolarSystemList() throws IOException {
        Request request = new Request.Builder()
                .url(solarSystemsUrl)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public void updateWorld() throws Exception {
        this.updateSolarSystemList();
    }

    private void updateSolarSystemList() throws FailedUpdateException {
        String rawData;
        try {
            rawData = this.fetchSolarSystemList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FailedUpdateException();
        }
    }

}
