package app.callback;

import okhttp3.Response;
import app.db.RethinkClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class WriteToRethinkCallback implements Callback {

  RethinkClient rc;
  String tableName;
  String dbName;
  JSONParser jp = new JSONParser();

  public WriteToRethinkCallback(String dbName, String tableName, Integer port, String username) {
    this.rc = new RethinkClient(port, username); // TODO figure out user name
    this.dbName = dbName;
    this.tableName = tableName;

    this.rc.createDatabase(this.dbName);
    this.rc.createTable(this.dbName, this.tableName);
  }

  public void call(Response response) {
    try {
      this.rc.insertData(this.dbName, this.tableName, response.body().string());
      // this.rc.insertData(this.dbName, this.tableName, jp.parse(response.body().string()));
    } catch (IOException e) {
      System.err.println("Error inserting data into database");
      e.printStackTrace();
    // } catch (ParseException pe) {
    //   System.err.println("Error parsing response");
    //   pe.printStackTrace();
    }
  }
}
