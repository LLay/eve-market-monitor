package application.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;


public class DataManager {

  public static final RethinkDB r = RethinkDB.r;

  public RethinkDB connect() {
    Connection connector = r.connection().db("eve-market-monitor").hostname("localhost").port(28015).connect();

    return r;
  }

  public void close() {
    r.connection().connect().close();
  }
}
