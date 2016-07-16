package app.polling;

import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;
import app.callback.*;

/** TODO
  * Used to set up scheduled calls to a 3rd party API. Specify the endpoint you'd like to pull from,
  * the period at which you'd like to poll the endpoint, the delay (if any) to start the polling,
  * and the callback function you would like to be called on the response.
  */
public class Poller {

  private TimerTask task;
  private long delay;
  private long period;
  private String url;
  private Timer timer;
  private Callback callback;

  /**
   * The class constructor TODO
   *
   * @param delay The delay between class instantiation and the first poll
   * @param period
   * @param url
   * @param callback
   */
  public Poller(long delay, long period, String url, Callback callback) {
    this.delay = delay;
    this.period = period;
    this.url = url; // TODO Probably make this a list at some point
    this.task = new MyTimerTask(url, callback);
    this.timer = new Timer();
    this.callback = callback;
  }

  public void poll() {
    timer.scheduleAtFixedRate(task, delay, period);
  }
}
