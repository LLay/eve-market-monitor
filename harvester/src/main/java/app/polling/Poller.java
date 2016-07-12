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
   * The class constructor
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
    this.task = new MyTimerTask(url, this);
    this.timer = new Timer();
    this.callback = callback;
  }

   class MyTimerTask extends TimerTask {

     Request request;
     Poller poller;
     OkHttpClient client = new OkHttpClient();

     MyTimerTask(String url, Poller poller) {
       this.request = new Request.Builder().url(url).build();
       this.poller = poller;
     }

     private void completeTask() {
        try {
          Response response = client.newCall(this.request).execute();
          poller.callback.call(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     @Override
     public void run() {
       completeTask();
    }
  }

  public TimerTask getTask() {
    return task;
  }
  public long getDelay() {
    return delay;
  }
  public long getPeriod() {
    return period;
  }

  public void poll() {
    timer.scheduleAtFixedRate(task, delay, period);
  }
}
