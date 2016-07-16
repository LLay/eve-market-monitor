package app.polling;

import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;
import app.callback.*;

/**
 * TODO
 */
public class MyTimerTask extends TimerTask {

  Request request;
  Callback callback;
  OkHttpClient client = new OkHttpClient();

  MyTimerTask(String url, Callback callback) {
    this.request = new Request.Builder().url(url).build();
    this.callback = callback;
  }

  @Override
  public void run() {
    try {
      Response response = client.newCall(this.request).execute();
      callback.call(response);
    } catch (IOException e) {
        e.printStackTrace();
    }
 }
}
