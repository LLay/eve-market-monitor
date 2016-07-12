package app.crestclient;

import app.world.SolarSystem;
import app.world.WorldPlaceType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Fetches data from the EVE CREST API
 */
public class CrestWorldClient {

    private static final String baseUrl = "https://crest-tq.eveonline.com/";
    private static final String solarSystemEndpoint = "solarsystems/";
    private static final String regionsEndpoint = "regions/";
    private static final String constellationsEndpoint = "constellations/";
    private static final String planetsEndpoint = "planets/";
    private static final String stargateEndpoint = "stargate/";

    private OkHttpClient client = new OkHttpClient();

    @Async
    public Future<String> getRawWorldPlaceList(WorldPlaceType type) throws IOException {
        switch (type) {
            case SolarSystem:   return this.fetchData(solarSystemEndpoint);
            case Constellation: return this.fetchData(constellationsEndpoint);
            case Region:        return this.fetchData(regionsEndpoint);
            default:            throw new IllegalArgumentException("Cannot get list for this place type");
        }
    }

    @Async
    public Future<JSONArray> getWorldPlaceList(WorldPlaceType type) throws IOException {
        return this.getPlaceList(this.getRawWorldPlaceList(type));
    }

    @Async
    private Future<String> fetchData(String endpoint) throws IOException {
        return this.fetchData(endpoint, null);
    }

    @Async
    private Future<String> fetchData(String endpoint, String args) throws IOException {
        if (args != null) {
            // do something
        } else {
            args = "";
        }

        Request request = new Request.Builder()
                .url(baseUrl + endpoint + args)
                .build();

        Response response = this.client.newCall(request).execute();
        return new AsyncResult<>(response.body().string());
    }

    /**
     * Gets a place list from a JSON String.  Returns an empty JSONArray if
     * unable to parse data.
     * @param data : JSON data
     * @return Array of JSONObjects representing places in the EVE world
     */
    private Future<JSONArray> getPlaceList(Future<String> data) {
        JSONParser jp = new JSONParser();
        JSONObject o;
        try {
            o = (JSONObject) jp.parse(data.get());
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.print("Poooop");
            return new AsyncResult<>(new JSONArray());
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.err.print("Uh oh");
            return new AsyncResult<>(new JSONArray());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.print("GAH");
            return new AsyncResult<>(new JSONArray());
        }

        int count = Integer.parseInt((String) o.get("totalCount_str"));
        JSONArray arr = (JSONArray) o.get("items");
        assert arr.size() == count;
        return new AsyncResult<>(arr);
    }
}
