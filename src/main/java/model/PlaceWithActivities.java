package model;

public class PlaceWithActivities {
  private PlaceLog placeLog;
  private WalkingActivity walkingActivity;

  public PlaceWithActivities(PlaceLog placeLog, WalkingActivity walkingActivity) {
    this.placeLog = placeLog;
    this.placeLog.setStartTime(null);
    this.placeLog.setEndTime(null);
    this.walkingActivity = walkingActivity;
  }

  public void setPlaceLog(PlaceLog placeLog) {
    this.placeLog = placeLog;
  }

  public PlaceLog getPlaceLog() {
    return placeLog;
  }

  public WalkingActivity getWalkingActivity() {
    return walkingActivity;
  }

  public void setWalkingActivity(WalkingActivity walkingActivity) {
    this.walkingActivity = walkingActivity;
  }

  public void updatePlaceLogWithActivities(PlaceLog placeLog, WalkingActivity walkingActivity) {
    addDuration(walkingActivity.getDuration());
    addDistance(walkingActivity.getDistance());
    addStep(walkingActivity.getStep());
    addCalorie(walkingActivity.getCalorie());
    addTimeSpent(placeLog.getTimeSpent());
  }

  private void addDuration(Double duration) {
    this.getWalkingActivity().addDuration(duration);
  }

  private void addDistance(Double distance) {
    this.getWalkingActivity().addDistance(distance);
  }

  private void addStep(Integer step) {
    this.getWalkingActivity().addStep(step);
  }

  private void addCalorie(Integer calorie) {
    this.getWalkingActivity().addCalorie(calorie);
  }

  private void addTimeSpent(Long timeSpent) {
    getPlaceLog().addTimeSpent(timeSpent);
  }
}
