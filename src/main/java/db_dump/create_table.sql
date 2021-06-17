CREATE SCHEMA IF NOT EXISTS FitnessApp;
USE FitnessApp;

DROP TABLE IF EXISTS WalkingActivity;
DROP TABLE IF EXISTS PlaceLog;
DROP TABLE IF EXISTS DailyEvent;

CREATE TABLE DailyEvent(
    Date DATE,
    Step INT,
    Calorie INT,
    WalkingDistance DECIMAL,
    WalkingDuration DECIMAL,
    TransportDistance DECIMAL,
    TransportDuration DECIMAL,
    CONSTRAINT pk_daily_event_date PRIMARY KEY(Date)
);

CREATE TABLE PlaceLog(
	Id BIGINT NOT NULL AUTO_INCREMENT,
    PlaceId INT,
    PlaceName VARCHAR(256),
    Longitude DECIMAL,
    Latitude DECIMAL,
    StartTime TIME,
    EndTime TIME,
    Date DATE,
    CONSTRAINT pk_place_log_id PRIMARY KEY(Id),
    CONSTRAINT fk_place_log_date FOREIGN KEY(Date)
		REFERENCES DailyEvent(Date)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE WalkingActivity(
	Id BIGINT NOT NULL AUTO_INCREMENT,
    PlaceLogId BIGINT,
    Duration DECIMAL,
    Distance DECIMAL,
    Step INT,
    Calories INT,
	CONSTRAINT pk_walking_activity_id PRIMARY KEY(Id),
    CONSTRAINT fk_walking_activity_place_log_id FOREIGN KEY(PlaceLogId)
		REFERENCES PlaceLog(Id)
        ON UPDATE CASCADE ON DELETE CASCADE
);