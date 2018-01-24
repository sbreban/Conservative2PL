package transactionmanager;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Transaction {
  private String id;
  private List<Operation> operations;
  private Set<Variable> readSet;
  private Set<Variable> writeSet;
  private int operationPointer = 0;

  public Transaction(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Operation> getOperations() {
    return operations;
  }

  public void setOperations(List<Operation> operations) {
    this.operations = operations;
  }

  public Set<Variable> getReadSet() {
    return readSet;
  }

  public void setReadSet(Set<Variable> readSet) {
    this.readSet = readSet;
  }

  public Set<Variable> getWriteSet() {
    return writeSet;
  }

  public void setWriteSet(Set<Variable> writeSet) {
    this.writeSet = writeSet;
  }

  public int getOperationPointer() {
    return operationPointer;
  }

  public void setOperationPointer(int operationPointer) {
    this.operationPointer = operationPointer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "id=" + id +
        '}';
  }
}
