package db_dump;

import static db_dump.ParserUtils.getDailyEventFromJson;
import static db_dump.ParserUtils.getDate;
import static db_dump.ParserUtils.getPlaceLogFromJson;
import static db_dump.ParserUtils.getWalkingActivityFromJson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.DailyEventDao;
import dao.PlaceLogDao;
import dao.WalkingActivityDao;
import java.io.FileReader;
import java.util.Date;
import model.DailyEvent;
import model.PlaceLog;
import model.WalkingActivity;

public class DumpDB {

  public static DailyEventDao dailyEventDao = DailyEventDao.getInstance();
  public static PlaceLogDao placeLogDao = PlaceLogDao.getInstance();
  public static WalkingActivityDao walkingActivityDao = WalkingActivityDao.getInstance();

  public static void main(String[] args) {

    JsonParser parser = new JsonParser();
    JsonArray array;
    try{
      array = (JsonArray) parser.parse(new FileReader("storyline.json"));
      for(int i = 0; i < array.size();i++ ){
        JsonObject obj = array.get(i).getAsJsonObject();
        Date date = getDate(obj.get("date").getAsString());

        try {
          JsonArray summary = obj.get("summary").getAsJsonArray();
          for (int j = 0; j < summary.size(); j++) {
            JsonObject activity = summary.get(j).getAsJsonObject();
            DailyEvent dailyEvent = getDailyEventFromJson(activity, date);
            dailyEventDao.createDailyEvent(dailyEvent);
          }

          JsonArray segment = obj.get("segments").getAsJsonArray();
          for (int j = 0; j < segment.size(); j++) {
            JsonObject element = segment.get(j).getAsJsonObject();
            if (!element.get("type").getAsString().equals("place")) {
              continue;
            }

            PlaceLog placeLog = getPlaceLogFromJson(element, date);
            if(placeLog==null){
              continue;
            }
            Long placeLogId = placeLogDao.createPlaceLog(placeLog);

            if (!element.has("activities")) {
              continue;
            }
            JsonArray activities = element.get("activities").getAsJsonArray();
            for (int k = 0; k < activities.size(); k++) {
              JsonObject activity = activities.get(k).getAsJsonObject();
              if (activity.get("activity").getAsString().equals("walking")) {
                WalkingActivity walkingActivity = getWalkingActivityFromJson(activity, placeLogId);
                walkingActivityDao.createWalkingActivity(walkingActivity);
              }
            }
          }
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }catch(Exception exception){
      exception.printStackTrace();
    }
  }
}
