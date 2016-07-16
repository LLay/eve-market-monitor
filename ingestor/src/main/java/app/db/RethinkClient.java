package app.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * TODO
 */
public class RethinkClient {

  private RethinkDB r;
  private Connection conn;
  private String username;
  // Store map of databases and their tables?

  /**
  * @param port the port number associated with the new rethink instance
  */
  public RethinkClient(Integer port, String username) {
    this.r = RethinkDB.r;
    // TODO wrap a timeout around this. should error out if port it not available
    this.conn = r.connection().hostname("localhost").port(port).connect();
    this.username = username;
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

  public void insertData(String dbName, String tableName, Object data) {
    this.r.db(dbName).table(tableName).insert(
      this.r.hashMap("data", data)
      .with("username", this.username)
      .with("insertion-time", this.r.now())).run(this.conn);
  }
}
