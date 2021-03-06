package app.marketstat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
public class MarketStatController {

  OkHttpClient client = new OkHttpClient();
  public static String url = "http://api.eve-central.com/api/marketstat?typeid=34&typeid=35&regionlimit=10000002";

  String run(String url) throws IOException {
    Request request = new Request.Builder()
    .url(url)
    .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  @RequestMapping("/marketstat")
  public String marketstat() throws IOException {
    return run(url);
  }
}
