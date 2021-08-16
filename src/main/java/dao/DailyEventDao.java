package dao;

import com.zaxxer.hikari.HikariDataSource;
import db_dump.ParserUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import model.DailyEvent;

public class DailyEventDao {

  private static HikariDataSource dataSource;
  public static DailyEventDao dailyEventDao = null;

  private DailyEventDao() {
    dataSource = DBCPDataSource.getDataSource();
  }

  public static DailyEventDao getInstance() {
    if (dailyEventDao == null) {
      dailyEventDao = new DailyEventDao();
    }
    return dailyEventDao;
  }

  public Integer createDailyEvent(DailyEvent dailyEvent) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    String insertQueryStatement =
        "INSERT INTO DailyEvent (Date, Activity, Step, Calorie, Distance, Duration) " +
            "VALUES (?,?,?,?,?,?)";
    try {
      conn = dataSource.getConnection();
      preparedStatement = conn
          .prepareStatement(insertQueryStatement, PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatement.setDate(1, new Date(dailyEvent.getDate().getTime()));
      preparedStatement.setString(2, dailyEvent.getActivity());
      if (dailyEvent.getStep() == null) {
        preparedStatement.setNull(3, Types.INTEGER);
      } else {
        preparedStatement.setInt(3, dailyEvent.getStep());
      }

      if (dailyEvent.getCalorie() == null) {
        preparedStatement.setNull(4, Types.INTEGER);
      } else {
        preparedStatement.setInt(4, dailyEvent.getCalorie());
      }
      preparedStatement.setDouble(5, dailyEvent.getDistance());
      preparedStatement.setDouble(6, dailyEvent.getDuration());

      // execute insert SQL statement
      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
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

  public List<DailyEvent> getDailyEventDetails(){
    String selectDailyEventDetails = "SELECT Id, Activity, IF(Distance IS NULL, 0, Distance) AS Distance, IF(Step IS NULL, 0, Step) AS Step, IF(Calorie IS NULL, 0, Calorie) AS Calorie, IF(Duration IS NULL, 0, Duration) AS Duration, Date FROM DailyEvent WHERE Activity='Walking' ORDER BY Date DESC LIMIT 100;\n;";
    Connection connection = null;
    PreparedStatement selectStatement = null;
    ResultSet resultSet = null;
    List<DailyEvent> dailyEvents = new ArrayList<>();
    try {
      connection = dataSource.getConnection();
      selectStatement = connection.prepareStatement(selectDailyEventDetails);
      resultSet = selectStatement.executeQuery();
      while (resultSet.next()) {
        dailyEvents.add(new DailyEvent(
            resultSet.getLong("Id"),
            resultSet.getDate("Date"),
            resultSet.getString("Activity"),
            resultSet.getInt("Step"),
            resultSet.getInt("Calorie"),
            resultSet.getDouble("Distance"),
            resultSet.getDouble("Duration")
        ));
      }
      return dailyEvents;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (selectStatement != null) {
        try {
          selectStatement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (resultSet != null) {
        try {
          resultSet.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return dailyEvents;
  }

  public List<DailyEvent> getDailyEventStatisticsByDateRange(String startDate, String endDate) {
    String selectDailyEventStatistics = "SELECT Activity, SUM(DIST) AS Distance, SUM(CAL) AS Calorie, SUM(STP) as Step, SUM(DUR) as Duration from (\n"
        + "SELECT IF(Distance IS NULL, 0, Distance) AS DIST, IF(Step IS NULL, 0, Step) AS STP, IF(Calorie IS NULL, 0, Calorie) AS CAL, IF(Duration IS NULL, 0, Duration) AS DUR, ACTIVITY, DATE\n"
        + "FROM DailyEvent) AS t1\n"
        + "WHERE DATE > ? AND Date <= ?\n"
        + "GROUP BY Activity;";
    Connection connection = null;
    PreparedStatement selectStatement = null;
    ResultSet resultSet = null;
    List<DailyEvent> dailyEvents = new ArrayList<>();
    try {
      connection = dataSource.getConnection();
      selectStatement = connection.prepareStatement(selectDailyEventStatistics);
      selectStatement.setDate(1, new Date(ParserUtils.getDate(startDate).getTime()));
      selectStatement.setDate(2, new Date(ParserUtils.getDate(endDate).getTime()));
      resultSet = selectStatement.executeQuery();
      while (resultSet.next()) {
        dailyEvents.add(new DailyEvent(
            resultSet.getString("Activity"),
            resultSet.getInt("Step"),
            resultSet.getInt("Calorie"),
            resultSet.getDouble("Distance"),
            resultSet.getDouble("Duration")
            ));
      }
      return dailyEvents;
    } catch (SQLException | ParseException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (selectStatement != null) {
        try {
          selectStatement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (resultSet != null) {
        try {
          resultSet.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return dailyEvents;
  }
}
