import java.util.*;

public class TransactionManager {

  List<Integer> transactionPointer = new ArrayList<>();
  Set<Integer> variables = new HashSet<>();
  Map<Integer, List<Operation>> transactionOperations = new HashMap<>();
  Map<Integer, Integer> transactionTimestamps = new HashMap<>();
  Map<Integer, List<Integer>> timeToTransactions = new HashMap<>();
  Map<Integer, Set<Integer>> transactionToReadSet = new HashMap<>();
  Map<Integer, Set<Integer>> transactionToWriteSet = new HashMap<>();
  private int systemTime = 0;
  private List<Integer> ready = new ArrayList<>();
  private List<Integer> running = new ArrayList<>();
  private List<Integer> done = new ArrayList<>();
  private Map<Integer, Integer> writeLocks = new HashMap<>();
  private Map<Integer, List<Integer>> readLocks = new HashMap<>();
  private Map<Integer, Integer> transactionOperationPointer = new HashMap<>();

  public List<Integer> getTransactionPointer() {
    return transactionPointer;
  }

  public void setTransactionPointer(List<Integer> transactionPointer) {
    this.transactionPointer = transactionPointer;
  }

  public Set<Integer> getVariables() {
    return variables;
  }

  public void setVariables(Set<Integer> variables) {
    this.variables = variables;
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

  public Map<Integer, Set<Integer>> getTransactionToReadSet() {
    return transactionToReadSet;
  }

  public void setTransactionToReadSet(Map<Integer, Set<Integer>> transactionToReadSet) {
    this.transactionToReadSet = transactionToReadSet;
  }

  public Map<Integer, Set<Integer>> getTransactionToWriteSet() {
    return transactionToWriteSet;
  }

  public void setTransactionToWriteSet(Map<Integer, Set<Integer>> transactionToWriteSet) {
    this.transactionToWriteSet = transactionToWriteSet;
  }

  public Map<Integer, Integer> getTransactionOperationPointer() {
    return transactionOperationPointer;
  }

  public void setTransactionOperationPointer(Map<Integer, Integer> transactionOperationPointer) {
    this.transactionOperationPointer = transactionOperationPointer;
  }

  public void run() {
    while (true) {
      List<Integer> transactions = timeToTransactions.get(systemTime);
      if (transactions != null) {
        ready.addAll(transactions);
      }
      for (int transaction : ready) {
        Set<Integer> read = transactionToReadSet.get(transaction);
        Set<Integer> write = transactionToWriteSet.get(transaction);

        try {
          for (Integer variable : write) {
            if (writeLocks.get(variable) != null) {
              throw new IllegalAccessException();
            }
          }
        } catch (IllegalAccessException e) {
          continue;
        }

        for (Integer variable : write) {
          writeLocks.put(variable, transaction);
        }

        for (Integer variable : read) {
          if (readLocks.get(variable) == null) {
            readLocks.put(variable, new ArrayList<>());
          }
          readLocks.get(variable).add(transaction);
        }
        System.out.println("All locks for " + transaction);
        running.add(transaction);
      }
      ready.removeAll(running);

      if (running.size() > 0) {
        int thisWillRun = running.get(0);
        System.out.println("This will run " + thisWillRun);

        int currentOperation = transactionOperationPointer.get(thisWillRun);
        System.out.println("This operation will run " + transactionOperations.get(thisWillRun).get(currentOperation));

        currentOperation++;
        transactionOperationPointer.put(thisWillRun, currentOperation);

        if (currentOperation > transactionOperations.get(thisWillRun).size() - 1) {
          System.out.println("All operations run for " + thisWillRun);
          List<Integer> toRemove = new ArrayList<>();
          toRemove.add(thisWillRun);
          for (Integer variable : variables) {
            if (writeLocks.get(variable) != null && writeLocks.get(variable) == thisWillRun) {
              writeLocks.put(variable, null);
            }
            if (readLocks.get(variable) != null && readLocks.get(variable).contains(thisWillRun)) {
              readLocks.get(variable).removeAll(toRemove);
            }
          }
          running.removeAll(toRemove);
          done.add(thisWillRun);
          System.out.println("All locks released");
        }
      }
      if (done.size() == transactionOperations.size()) {
        break;
      }
      systemTime++;
    }
  }
}
