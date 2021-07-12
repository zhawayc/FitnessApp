package db_dump;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import model.DailyEvent;
import model.PlaceLog;
import model.WalkingActivity;

public class ParserUtils {
  public static Date getDate(String string) throws ParseException {
    SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
    Date date = format.parse(string);
    return date;
  }

  public static LocalTime getLocalTime(String string){
    DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
    return LocalTime.parse(string.substring(0, string.length()-5),timeFormatter);
  }

  public static DailyEvent getDailyEventFromJson(JsonObject activity, Date date) {
    DailyEvent dailyEvent = new DailyEvent();
    dailyEvent.setDate(date);

    dailyEvent.setActivity(activity.get("activity").getAsString());
    dailyEvent.setDuration(activity.get("duration").getAsDouble());
    dailyEvent.setDistance(activity.get("distance").getAsDouble());
    if (activity.has("calories")) {
      dailyEvent.setCalorie(activity.get("calories").getAsInt());
    }
    if (activity.has("steps")) {
      dailyEvent.setStep(activity.get("steps").getAsInt());
    }
    return dailyEvent;
  }

  public static PlaceLog getPlaceLogFromJson(JsonObject segment, Date date) {
    PlaceLog placeLog = new PlaceLog();
    placeLog.setDate(date);
    placeLog.setStartTime(getLocalTime(segment.get("startTime").getAsString()));
    placeLog.setEndTime(getLocalTime(segment.get("endTime").getAsString()));
    JsonObject place = segment.get("place").getAsJsonObject();
    if (!place.has("name")) {
      return null;
    }
    placeLog.setPlaceName(place.get("name").getAsString());
    placeLog.setPlaceId(place.get("id").getAsInt());
    JsonObject location = place.get("location").getAsJsonObject();
    placeLog.setLatitude(location.get("lat").getAsDouble());
    placeLog.setLongitude(location.get("lon").getAsDouble());
    return placeLog;
  }

  public static WalkingActivity getWalkingActivityFromJson(JsonObject placeActivity, Long placeId) {
    WalkingActivity walkingActivity = new WalkingActivity();
    walkingActivity.setPlaceLogId(placeId);
    walkingActivity.setDuration(placeActivity.get("duration").getAsDouble());
    walkingActivity.setDistance(placeActivity.get("distance").getAsDouble());
    walkingActivity.setCalorie(placeActivity.get("calories").getAsInt());
    walkingActivity.setStep(placeActivity.get("steps").getAsInt());
    return walkingActivity;
  }
}
