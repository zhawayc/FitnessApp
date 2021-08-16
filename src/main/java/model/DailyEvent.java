package model;

import java.util.Date;

public class DailyEvent {
  private Long id;
  private Date date;
  private String activity;
  private Integer step;
  private Integer calorie;
  private Double distance;
  private Double duration;

  public DailyEvent() {
  }

  public DailyEvent(Long id, Date date, String activity, Integer step, Integer calorie, Double distance, Double duration) {
    this.id = id;
    this.date = date;
    this.activity = activity;
    this.step = step;
    this.calorie = calorie;
    this.distance = distance;
    this.duration = duration;
  }

  public DailyEvent(Date date, Integer step, String activity, Integer calorie, Double distance, Double duration) {
    this.date = date;
    this.step = step;
    this.activity = activity;
    this.calorie = calorie;
    this.distance = distance;
    this.duration = duration;
  }

  public DailyEvent(String activity, Integer step, Integer calorie, Double distance, Double duration) {
    this.step = step;
    this.activity = activity;
    this.calorie = calorie;
    this.distance = distance;
    this.duration = duration;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Integer getStep() {
    return step;
  }

  public void setStep(Integer step) {
    this.step = step;
  }

  public String getActivity() {
    return activity;
  }

  public void setActivity(String activity) {
    this.activity = activity;
  }

  public Integer getCalorie() {
    return calorie;
  }

  public void setCalorie(Integer calorie) {
    this.calorie = calorie;
  }

  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public Double getDuration() {
    return duration;
  }

  public void setDuration(Double duration) {
    this.duration = duration;
  }
}
