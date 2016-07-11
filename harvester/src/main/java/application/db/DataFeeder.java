package application.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Cursor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

public class DataFeeder {

  public void feed(String table, String data) {
    DataManager dataManager = new DataManager();
    JSONObject commodity = parse(data);

    RethinkDB connector = dataManager.connect();
    // This is a lot of hard-coded shit
//    dataManager.createTable(table);
    connector.table(table).insert(commodity);
    dataManager.printTable(table);

    dataManager.close();
  }

  public JSONObject parse(String data) {
    try {
      JSONParser parser = new JSONParser();
      JSONArray dataArray = (JSONArray) parser.parse(data);
      JSONObject commodity = (JSONObject) dataArray.get(0);
      return commodity;
    } catch (ParseException e) {
      System.out.println("Someone done fucked up. Was it you?");
      return null;
    }
  }
}
