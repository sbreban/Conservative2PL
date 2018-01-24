package airlines;

public class Route {
  private int id;
  private String source;
  private String destination;

  public Route(int id, String source, String destination) {
    this.id = id;
    this.source = source;
    this.destination = destination;
  }

  @Override
  public String toString() {
    return "Route{" +
        "id=" + id +
        ", source='" + source + '\'' +
        ", destination='" + destination + '\'' +
        '}';
  }
}
