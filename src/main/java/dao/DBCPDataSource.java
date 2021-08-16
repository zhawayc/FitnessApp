package dao;

import com.zaxxer.hikari.HikariDataSource;

public class DBCPDataSource {
  private static HikariDataSource dataSource;

  //private static final String HOST_NAME = "database-1.cdgrvwpcapvo.us-east-1.rds.amazonaws.com";
  private static final String HOST_NAME = "localhost";
  private static final String PORT = "3306";
  private static final String DATABASE = "FitnessApp";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "614a7159509";

  static {
    dataSource = new HikariDataSource();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
    String url = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", HOST_NAME, PORT, DATABASE);

    dataSource.setJdbcUrl(url);
    dataSource.setUsername(USERNAME);
    dataSource.setPassword(PASSWORD);
  }

  public static HikariDataSource getDataSource() {
    return dataSource;
  }
}
