package app.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

public class DataManager {

    public static final RethinkDB r = RethinkDB.r;

    public void test() {
        Connection conn = r.connection().hostname("localhost").port(28015).connect();
        // DO MANY STUFF
    }
}