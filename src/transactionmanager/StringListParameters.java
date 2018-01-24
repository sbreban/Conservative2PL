package transactionmanager;

import java.util.List;

public class StringListParameters implements OperationParameter {
  private List<String> parameters;

  public StringListParameters(List<String> parameters) {
    this.parameters = parameters;
  }

  public List<String> getParameters() {
    return parameters;
  }

  public void setParameters(List<String> parameters) {
    this.parameters = parameters;
  }

  public void addParameter(String parameter) {
    parameters.add(parameter);
  }

  @Override
  public String toString() {
    return "StringListParameters{" +
        "parameters=" + parameters +
        '}';
  }
}
