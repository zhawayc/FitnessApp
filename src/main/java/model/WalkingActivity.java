package model;

public class WalkingActivity {
  private Long id;
  private Long placeLogId;
  private Double duration;
  private Double distance;
  private Integer step;
  private Integer calorie;

  public WalkingActivity() {
  }

  public WalkingActivity(Double duration, Double distance, Integer step, Integer calorie) {
    this.duration = duration;
    this.distance = distance;
    this.step = step;
    this.calorie = calorie;
  }

  public WalkingActivity(Long id, Long placeLogId, Double duration, Double distance,
      Integer step, Integer calorie) {
    this.id = id;
    this.placeLogId = placeLogId;
    this.duration = duration;
    this.distance = distance;
    this.step = step;
    this.calorie = calorie;
  }

  public WalkingActivity(Long placeLogId, Double duration, Double distance, Integer step,
      Integer calorie) {
    this.placeLogId = placeLogId;
    this.duration = duration;
    this.distance = distance;
    this.step = step;
    this.calorie = calorie;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPlaceLogId() {
    return placeLogId;
  }

  public void setPlaceLogId(Long placeLogId) {
    this.placeLogId = placeLogId;
  }

  public Double getDuration() {
    return nonNullableDouble(duration);
  }

  public void setDuration(Double duration) {
    this.duration = duration;
  }

  public Double getDistance() {
    return nonNullableDouble(distance);
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public Integer getStep() {
    return nonNullableInteger(step);
  }

  public void setStep(Integer step) {
    this.step = step;
  }

  public Integer getCalorie() {
    return nonNullableInteger(calorie);
  }

  public void setCalorie(Integer calorie) {
    this.calorie = calorie;
  }

  private Double nonNullableDouble(Double num) {
    return num == null ? 0.0 : num;
  }

  private Integer nonNullableInteger(Integer num) {
    return num == null ? 0 : num;
  }

  public void addDuration(Double duration) {
    setDuration(getDuration() + duration);
  }

  public void addDistance(Double distance) {
    setDistance(getDistance() + distance);
  }

  public void addStep(Integer step) {
    setStep(getStep() + step);
  }

  public void addCalorie(Integer calorie) {
    setCalorie(getCalorie() + calorie);
  }
}
