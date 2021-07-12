package dao;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import model.DailyEvent;

public class DailyEventDao {
  private static HikariDataSource dataSource;
  public static DailyEventDao dailyEventDao = null;

  private DailyEventDao() {
    dataSource = DBCPDataSource.getDataSource();
  }

  public static DailyEventDao getInstance(){
    if(dailyEventDao == null){
      dailyEventDao = new DailyEventDao();
    }
    return dailyEventDao;
  }

  public Integer createDailyEvent(DailyEvent dailyEvent) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    String insertQueryStatement = "INSERT INTO DailyEvent (Date, Activity, Step, Calorie, Distance, Duration) " +
        "VALUES (?,?,?,?,?,?)";
    try {
      conn = dataSource.getConnection();
      preparedStatement = conn.prepareStatement(insertQueryStatement, PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatement.setDate(1, new Date(dailyEvent.getDate().getTime()));
      preparedStatement.setString(2, dailyEvent.getActivity());
      if(dailyEvent.getStep() == null){
        preparedStatement.setNull(3, Types.INTEGER);
      }else{
        preparedStatement.setInt(3, dailyEvent.getStep());
      }

      if(dailyEvent.getCalorie() == null){
        preparedStatement.setNull(4, Types.INTEGER);
      }else{
        preparedStatement.setInt(4, dailyEvent.getCalorie());
      }
      preparedStatement.setDouble(5, dailyEvent.getDistance());
      preparedStatement.setDouble(6, dailyEvent.getDuration());

      // execute insert SQL statement
      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if(rs.next()){
        return rs.getInt(1);
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
    return 0;
  }
}
