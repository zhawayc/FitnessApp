package model;

import java.time.LocalTime;
import java.util.Date;

public class PlaceLog {
  private Long id;
  private Integer placeId;
  private String placeName;
  private Double longitude;
  private Double latitude;
  private LocalTime startTime;
  private LocalTime endTime;
  private Date date;

  public PlaceLog() {
  }

  public PlaceLog(Long id, Integer placeId, String placeName, Double longitude,
      Double latitude, LocalTime startTime, LocalTime endTime, Date date) {
    this.id = id;
    this.placeId = placeId;
    this.placeName = placeName;
    this.longitude = longitude;
    this.latitude = latitude;
    this.startTime = startTime;
    this.endTime = endTime;
    this.date = date;
  }

  public PlaceLog(Integer placeId, String placeName, Double longitude, Double latitude,
      LocalTime startTime, LocalTime endTime, Date date) {
    this.placeId = placeId;
    this.placeName = placeName;
    this.longitude = longitude;
    this.latitude = latitude;
    this.startTime = startTime;
    this.endTime = endTime;
    this.date = date;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getPlaceId() {
    return placeId;
  }

  public void setPlaceId(Integer placeId) {
    this.placeId = placeId;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
