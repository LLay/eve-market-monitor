package application;

import com.rethinkdb.net.Cursor;
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

  private TimerTask task;
  private long delay;
  private long period;
  private String url;
  private Timer timer;

  public Poller(long delay, long period, String table, String url) {
    this.delay = delay;
    this.period = period;
    this.url = url; // Probably make this a list at some point
    this.task = new MyTimerTask(table, url);
    this.timer = new Timer();
  }

  class MyTimerTask extends TimerTask {

    String table;
    Request request;
    OkHttpClient client = new OkHttpClient();

    MyTimerTask(String table, String url) {
      this.table = table;
      this.request = new Request.Builder().url(url).build();
    }

    private void completeTask() {
      try {
        DataFeeder dataFeeder = new DataFeeder();
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
