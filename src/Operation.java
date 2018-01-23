import java.util.Objects;

public class Operation {
  private String instruction;
  private int variable;

  public Operation(String instruction, int variable) {
    this.instruction = instruction;
    this.variable = variable;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public int getVariable() {
    return variable;
  }

  public void setVariable(int variable) {
    this.variable = variable;
  }

  @Override
  public String toString() {
    return instruction + " " + variable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Operation operation = (Operation) o;
    return variable == operation.variable &&
        Objects.equals(instruction, operation.instruction);
  }

  @Override
  public int hashCode() {

    return Objects.hash(instruction, variable);
  }
}
