import java.util.*;

public class TransactionManager {

  List<Integer> transactionPointer = new ArrayList<>();
  Set<Integer> variable = new HashSet<>();
  Map<Integer, List<Operation>> transactionOperations = new HashMap<>();
  Map<Integer, Integer> transactionTimestamps = new HashMap<>();
  Map<Integer, List<Integer>> timeToTransactions = new HashMap<>();
  private int systemTime = 0;

  public List<Integer> getTransactionPointer() {
    return transactionPointer;
  }

  public void setTransactionPointer(List<Integer> transactionPointer) {
    this.transactionPointer = transactionPointer;
  }

  public Set<Integer> getVariable() {
    return variable;
  }

  public void setVariable(Set<Integer> variable) {
    this.variable = variable;
  }

  public Map<Integer, List<Operation>> getTransactionOperations() {
    return transactionOperations;
  }

  public void setTransactionOperations(Map<Integer, List<Operation>> transactionOperations) {
    this.transactionOperations = transactionOperations;
  }

  public Map<Integer, Integer> getTransactionTimestamps() {
    return transactionTimestamps;
  }

  public void setTransactionTimestamps(Map<Integer, Integer> transactionTimestamps) {
    this.transactionTimestamps = transactionTimestamps;
  }

  public Map<Integer, List<Integer>> getTimeToTransactions() {
    return timeToTransactions;
  }

  public void setTimeToTransactions(Map<Integer, List<Integer>> timeToTransactions) {
    this.timeToTransactions = timeToTransactions;
  }

  public void run() {
    while (true) {
      int transaction = transactionTimestamps.get(systemTime);
    }
  }
}
