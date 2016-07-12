package app.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.gen.exc.ReqlOpFailedError;

import java.io.IOException;

public class RethinkClient {

  public static RethinkDB r;
  Connection conn;

  /**
  * @param port the port number associated with the new rethink instance
  */
  public RethinkClient(Integer port) {
    this.r = RethinkDB.r;
    this.conn = r.connection().hostname("localhost").port(port).connect();
  }

  public void createDatabase(String dbName) {
    try {
      r.dbCreate(dbName).run(conn);
    } catch (ReqlOpFailedError e) {
      System.err.println("Error creating database " + dbName);
      System.err.println(e);
    }
  }

  public void createTable(String dbName, String tableName) {
    try {
      r.db(dbName).tableCreate(tableName).run(conn);
    } catch (ReqlOpFailedError e) {
      System.err.println("Error creating table " + tableName + "in database " + dbName);
      System.err.println(e);
    }
  }

  public void insertData(String dbName, String tableName, String data) { // FIXME not Response, JSON
    // try {
      r.db(dbName).table(tableName).insert(data).run(conn);
    // } catch (IOException e) {
    //   System.err.println("Error inserting data into " + tableName + "in database " + dbName);
    //   e.printStackTrace();
    // }
  }
}
