package airlines;

import java.sql.Timestamp;

public class Flight {
  private Route route;
  private Timestamp departure;
  private Timestamp arrival;

  public Flight(Route route, Timestamp departure, Timestamp arrival) {
    this.route = route;
    this.departure = departure;
    this.arrival = arrival;
  }

  @Override
  public String toString() {
    return "Flight{" +
        "route=" + route +
        ", departure=" + departure +
        ", arrival=" + arrival +
        '}';
  }
}
