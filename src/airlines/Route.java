package airlines;

import transactionmanager.OperationParameter;

public class Route implements OperationParameter {
  private int id;
  private String source;
  private String destination;

  public Route(int id, String source, String destination) {
    this.id = id;
    this.source = source;
    this.destination = destination;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
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
