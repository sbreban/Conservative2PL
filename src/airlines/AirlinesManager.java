package airlines;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AirlinesManager {
  private Connection connection;

  public AirlinesManager() {
    // create a database connection
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:airlines.db");
    } catch (SQLException e) {
      System.err.println(e);
    }
  }

  public List<Flight> getAllFlights() {
    Statement statement = null;
    ResultSet rs = null;
    List<Flight> flights = new ArrayList<>();
    try {
      statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      //language= SQL
      String query = "SELECT " +
          "  r.id, " +
          "  r.source, " +
          "  r.destination, " +
          "  f.departure_time, " +
          "  f.arrival_time " +
          "FROM flights f " +
          "  INNER JOIN routes r ON f.route_id = r.id";
      rs = statement.executeQuery(query);
      while (rs.next()) {
        // read the result set
        int routeId = rs.getInt(1);
        String source = rs.getString(2);
        String destination = rs.getString(3);
        Timestamp departure = rs.getTimestamp(4);
        Timestamp arrival = rs.getTimestamp(5);
        Route route = new Route(routeId, source, destination);
        Flight flight = new Flight(route, departure, arrival);

        flights.add(flight);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (statement != null) {
          statement.close();
        }
      } catch (SQLException e) {
        System.err.println(e);
      }
    }
    return flights;
  }

  public void close() {
    try {
      if (connection != null)
        connection.close();
    } catch (SQLException e) {
      // connection close failed.
      System.err.println(e);
    }
  }
}
