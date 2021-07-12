package dao;

import com.zaxxer.hikari.HikariDataSource;
import db_dump.ParserUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.PlaceLog;

public class PlaceLogDao {
  private static HikariDataSource dataSource;
  public static PlaceLogDao placeLogDao = null;

  private PlaceLogDao() {
    dataSource = DBCPDataSource.getDataSource();
  }

  public static PlaceLogDao getInstance(){
    if(placeLogDao == null){
      placeLogDao = new PlaceLogDao();
    }
    return placeLogDao;
  }

  public Long createPlaceLog(PlaceLog placeLog) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    String insertQueryStatement = "INSERT INTO PlaceLog (PlaceId, PlaceName, Longitude, Latitude, StartTime, EndTime, Date) " +
        "VALUES (?,?,?,?,?,?,?)";
    try {
      conn = dataSource.getConnection();
      preparedStatement = conn.prepareStatement(insertQueryStatement, PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, placeLog.getPlaceId());
      preparedStatement.setString(2, placeLog.getPlaceName());
      preparedStatement.setDouble(3, placeLog.getLongitude());
      preparedStatement.setDouble(4, placeLog.getLatitude());
      preparedStatement.setTime(5, Time.valueOf(placeLog.getStartTime()));
      preparedStatement.setTime(6, Time.valueOf(placeLog.getEndTime()));
      preparedStatement.setDate(7, new Date(placeLog.getDate().getTime()));

      // execute insert SQL statement
      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if(rs.next()){
        return rs.getLong(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
    return 0L;
  }

  public List<PlaceLog> getPlaceLogByDate(String date) {
    String selectPlaceLogs="SELECT Id, PlaceId, PlaceName, Longitude, Latitude, StartTime, EndTime, Date FROM PlaceLog WHERE Date=?;";
    Connection connection=null;
    PreparedStatement selectStatement=null;
    ResultSet resultSet=null;
    List<PlaceLog> placeLogs=new ArrayList<>();
    try {
      connection=dataSource.getConnection();
      selectStatement=connection.prepareStatement(selectPlaceLogs);
      selectStatement.setDate(1, new Date(ParserUtils.getDate(date).getTime()));
      resultSet=selectStatement.executeQuery();
      while(resultSet.next()) {
        placeLogs.add(new PlaceLog(
            resultSet.getLong("Id"),
            resultSet.getInt("PlaceId"),
            resultSet.getString("PlaceName"),
            resultSet.getDouble("Longitude"),
            resultSet.getDouble("Latitude"),
            LocalTime.of(resultSet.getTime("StartTime").getHours(), resultSet.getTime("StartTime").getMinutes(), resultSet.getTime("StartTime").getSeconds()),
            LocalTime.of(resultSet.getTime("EndTime").getHours(), resultSet.getTime("StartTime").getMinutes(), resultSet.getTime("StartTime").getSeconds()),
            new Date(ParserUtils.getDate(date).getTime())));
      }
      return placeLogs;
    }catch (SQLException | ParseException e) {
      e.printStackTrace();
    }finally {
      if(connection!=null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if(selectStatement!=null) {
        try {
          selectStatement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if(resultSet!=null) {
        try {
          resultSet.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return placeLogs;
  }
}
