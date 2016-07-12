package app.callback;

import okhttp3.Response;
import app.db.RethinkClient;

public class WriteToRethinkCallback implements Callback {

  RethinkClient rc;

  public WriteToRethinkCallback() {
    this.rc = RethinkClient(28015);

    this.rc.createDatabase("eve");
    this.rc.createTable("marketstat");
  }

  public void call(Response response) {
    this.rc.insert(response);
  }
}
