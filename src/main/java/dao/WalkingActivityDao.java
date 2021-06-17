package dao;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import model.DailyEvent;
import model.WalkingActivity;

public class WalkingActivityDao {
  private static HikariDataSource dataSource;
  public static WalkingActivityDao walkingActivityDao = null;

  private WalkingActivityDao() {
    dataSource = DBCPDataSource.getDataSource();
  }

  public static WalkingActivityDao getInstance(){
    if(walkingActivityDao == null){
      walkingActivityDao = new WalkingActivityDao();
    }
    return walkingActivityDao;
  }

  public void createWalkingActivity(WalkingActivity walkingActivity) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    String insertQueryStatement = "INSERT INTO WalkingActivity (PlaceLogId, Duration, Distance, Step, Calorie) " +
        "VALUES (?,?,?,?,?)";
    try {
      conn = dataSource.getConnection();
      preparedStatement = conn.prepareStatement(insertQueryStatement);
      preparedStatement.setLong(1, walkingActivity.getPlaceLogId());
      if(walkingActivity.getDuration()==null){
        preparedStatement.setNull(2, Types.DECIMAL);
      }else{
        preparedStatement.setDouble(2, walkingActivity.getDuration());
      }
      preparedStatement.setDouble(3, walkingActivity.getDistance());
      preparedStatement.setInt(4, walkingActivity.getStep());
      preparedStatement.setInt(5, walkingActivity.getCalorie());

      // execute insert SQL statement
      preparedStatement.executeUpdate();

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
  }
}
