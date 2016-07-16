package app.callback;

import okhttp3.Response;
import app.db.RethinkClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class WriteToRethinkCallback implements Callback {

  RethinkClient rc;

  public WriteToRethinkCallback() {
    this.rc = new RethinkClient(28015);

    this.rc.createDatabase("eve");
    this.rc.createTable("eve", "marketstat");
  }

  public void call(Response response) {
    try {
      this.rc.insertData("eve", "marketstat", response.body().string());
    } catch (IOException e) {
      System.err.println("Error inserting data into database");
      e.printStackTrace();
    }
  }
}
