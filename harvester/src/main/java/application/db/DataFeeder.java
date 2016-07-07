package application.db;

import com.rethinkdb.RethinkDB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

public class DataFeeder {

  @Autowired
  DataManager dataManager;

  @Autowired
  JSONParser jsonParser;


  public void DataFeeder(String db) {

  }

  public void feed(String table, String data) {
    JSONObject commodity = parse(data);

    RethinkDB connector = dataManager.connect();
    connector.table(table).insert(commodity);

    dataManager.close();
  }

  public JSONObject parse(String data) {
    try {
      JSONArray dataArray = (JSONArray) jsonParser.parse(data);
      JSONObject commodity = (JSONObject) dataArray.get(0);
      return commodity;
    } catch (ParseException e) {
      System.out.println("Someone done fucked up. Was it you?");
      return null;
    }
  }
}
