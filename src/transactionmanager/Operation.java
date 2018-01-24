package transactionmanager;

import java.util.Objects;

public class Operation {
  private String instruction;
  private Variable variable;
  private OperationParameter parameters;

  public Operation(String instruction, Variable variable, StringListParameters parameters) {
    this.instruction = instruction;
    this.variable = variable;
    this.parameters = parameters;
  }

  public Operation(String instruction, Variable variable) {
    this.instruction = instruction;
    this.variable = variable;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public Variable getVariable() {
    return variable;
  }

  public void setVariable(Variable variable) {
    this.variable = variable;
  }

  public OperationParameter getParameters() {
    return parameters;
  }

  public void setParameters(OperationParameter parameters) {
    this.parameters = parameters;
  }

  @Override
  public String toString() {
    return instruction + " " + variable + " " + parameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Operation operation = (Operation) o;
    return Objects.equals(instruction, operation.instruction) &&
        Objects.equals(variable, operation.variable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instruction, variable);
  }
}
