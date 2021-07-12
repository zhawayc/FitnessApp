package processor;

import dao.PlaceLogDao;
import dao.WalkingActivityDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.PlaceLog;
import model.PlaceWithActivities;
import model.WalkingActivity;

public class PlaceActivityProcessor {
  private PlaceLogDao placeLogDao;
  private WalkingActivityDao walkingActivityDao;
  private static PlaceActivityProcessor placeActivityProcessor;

  private PlaceActivityProcessor() {
    placeLogDao = PlaceLogDao.getInstance();
    walkingActivityDao = WalkingActivityDao.getInstance();
  }

  public static PlaceActivityProcessor getInstance() {
    if (placeActivityProcessor == null) {
      placeActivityProcessor = new PlaceActivityProcessor();
    }
    return placeActivityProcessor;
  }

  public List<PlaceWithActivities> findPlaceLogWithActivitiesByDate(String date) {
    List<PlaceWithActivities> lst = new ArrayList<>();
    List<PlaceLog> placeLogs = placeLogDao.getPlaceLogByDate(date);
    Map<Integer, PlaceWithActivities> map = new HashMap<>();
    for (PlaceLog placeLog: placeLogs) {
      Long logId = placeLog.getId();
      WalkingActivity walkingActivity = walkingActivityDao.getWalkingActivitiesByLogId(logId);
      Integer placeId = placeLog.getPlaceId();
      if (map.containsKey(placeId)) {
        PlaceWithActivities placeWithActivities = map.get(placeId);
        placeWithActivities.updatePlaceLogWithActivities(placeLog, walkingActivity);
      }
      else {
        map.put(placeId, new PlaceWithActivities(placeLog, walkingActivity));
      }
    }
    lst.addAll(map.values());
    return lst;
  }
}
