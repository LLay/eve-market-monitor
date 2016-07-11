package application.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import org.springframework.stereotype.Service;

@Service
public class DataManager {

  public static final RethinkDB r = RethinkDB.r;
  protected Connection connector;

  public RethinkDB connect() {

    // TODO: Check if db "eveMarketMonitor" exists and create if not
    this.connector = r.connection().hostname("localhost").port(28015).connect();

    return r;
  }

  public void close() {
    r.connection().connect().close();
  }

  public void createTable(String table) {
    // TODO: Check if table exists before creating
    r.db("test").tableCreate(table).run(connector);
  }

  public void printTable(String table) {
    Cursor cursor = r.table(table).run(connector);
    for (Object tableShit : cursor) {
      System.out.println(tableShit);
    }
  }
}
