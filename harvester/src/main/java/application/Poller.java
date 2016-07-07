package application;

import org.springframework.beans.factory.annotation.Autowired;
import application.db.DataFeeder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;

import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;

public class Poller {

  @Autowired
  DataFeeder dataFeeder;

  private TimerTask task;
  private long delay;
  private long period;
  private String url;
  private Timer timer;
  private String table;

  public Poller(long delay, long period, String table, String url) {
    this.delay = delay;
    this.period = period;
    this.table = table;
    this.url = url; // Probably make this a list at some point
    this.task = new MyTimerTask(url);
    this.timer = new Timer();
  }

  class MyTimerTask extends TimerTask {

    Request request;
    OkHttpClient client = new OkHttpClient();

    MyTimerTask(String url) {
      this.request = new Request.Builder().url(url).build();
    }

    private void completeTask() {
      try {
        String response = client.newCall(this.request).execute().body().string();
        dataFeeder.feed(table, response);
        System.out.println(response);
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
