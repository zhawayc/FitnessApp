package dao;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
      if(walkingActivity.getDuration().equals(null)){
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

  public WalkingActivity getWalkingActivitiesByLogId(Long logId) {
    String selectWalkingActivities="SELECT SUM(Duration) AS SUM_DURATION, SUM(Distance) AS SUM_DISTANCE, SUM(Step) AS SUM_STEP, SUM(Calorie) AS SUM_CALORIE FROM WalkingActivity WHERE PlaceLogId=?;";
    Connection connection=null;
    PreparedStatement selectStatement=null;
    ResultSet resultSet=null;
    try {
      connection=dataSource.getConnection();
      selectStatement=connection.prepareStatement(selectWalkingActivities);
      selectStatement.setLong(1, logId);
      resultSet=selectStatement.executeQuery();
      if(resultSet.next()) {
        return new WalkingActivity(
            resultSet.getDouble("SUM_Duration"),
            resultSet.getDouble("SUM_Distance"),
            resultSet.getInt("SUM_Step"),
            resultSet.getInt("SUM_Calorie"));
      }
      return null;
    }catch (SQLException e) {
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
    return null;
  }
}
