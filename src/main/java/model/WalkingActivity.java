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
    return duration;
  }

  public void setDuration(Double duration) {
    this.duration = duration;
  }

  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public Integer getStep() {
    return step;
  }

  public void setStep(Integer step) {
    this.step = step;
  }

  public Integer getCalorie() {
    return calorie;
  }

  public void setCalorie(Integer calorie) {
    this.calorie = calorie;
  }
}
