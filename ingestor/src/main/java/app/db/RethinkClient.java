package app.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import org.json.simple.JSONObject;

import java.io.IOException;

public class RethinkClient {

  private RethinkDB r;
  private Connection conn;

  /**
  * @param port the port number associated with the new rethink instance
  */
  public RethinkClient(Integer port) {
    this.r = RethinkDB.r;
    this.conn = r.connection().hostname("localhost").port(port).connect();
  }

  // TODO Return reference to database?
  public void createDatabase(String dbName) {
    try {
      System.out.println("Creating  database " + dbName);
      r.dbCreate(dbName).run(conn);
    } catch (ReqlOpFailedError e) {
      System.err.println("Error creating database " + dbName);
      System.err.println(e);
    }
  }

  public void createTable(String dbName, String tableName) {
    try {
      System.out.println("Creating  table " + tableName + "in database " + dbName);
      r.db(dbName).tableCreate(tableName).run(conn);
    } catch (ReqlOpFailedError e) {
      System.err.println("Error creating table " + tableName + "in database " + dbName);
      System.err.println(e);
    }
  }

// TODO figure out username
  public void insertData(String dbName, String tableName, String data) { // FIXME data is not String, JSON
    this.r.db(dbName).table(tableName).insert(
      this.r.hashMap("text", data)
      .with("username", "llay")
      .with("time", this.r.now())).run(this.conn);
  }
}
