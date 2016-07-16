package application;
import app.polling.Poller;
import app.callback.*;

public class Application {
  public static void main(String[] args) {

    // Poller poller = new Poller(0, 10000, "http://api.eve-central.com/api/marketstat?typeid=34&typeid=35&regionlimit=10000002", new PrintResponseCallback());
    Poller poller = new Poller(0,
      10000,
      "https://crest-tq.eveonline.com/market/prices/",
      new WriteToRethinkCallback("eve", "marketstat", "admin"));
    poller.poll();
  }
}
