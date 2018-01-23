import java.util.*;

public class TransactionManager {

  private Set<Variable> variables;
  private Map<Transaction, List<Operation>> transactionOperations;
  private Map<Transaction, Set<Variable>> transactionToReadSet;
  private Map<Transaction, Set<Variable>> transactionToWriteSet;
  private Map<Transaction, Integer> transactionTimestamps;
  private Map<Transaction, Integer> transactionOperationPointer;
  private Map<Integer, List<Transaction>> timeToTransactions;
  private int systemTime = 0;
  private List<Transaction> ready = new ArrayList<>();
  private List<Transaction> running = new ArrayList<>();
  private List<Transaction> done = new ArrayList<>();
  private Map<Variable, Transaction> writeLocks = new HashMap<>();
  private Map<Variable, List<Transaction>> readLocks = new HashMap<>();

  public Set<Variable> getVariables() {
    return variables;
  }

  public void setVariables(Set<Variable> variables) {
    this.variables = variables;
  }

  public Map<Transaction, List<Operation>> getTransactionOperations() {
    return transactionOperations;
  }

  public void setTransactionOperations(Map<Transaction, List<Operation>> transactionOperations) {
    this.transactionOperations = transactionOperations;
  }

  public Map<Transaction, Integer> getTransactionTimestamps() {
    return transactionTimestamps;
  }

  public void setTransactionTimestamps(Map<Transaction, Integer> transactionTimestamps) {
    this.transactionTimestamps = transactionTimestamps;
  }

  public Map<Integer, List<Transaction>> getTimeToTransactions() {
    return timeToTransactions;
  }

  public void setTimeToTransactions(Map<Integer, List<Transaction>> timeToTransactions) {
    this.timeToTransactions = timeToTransactions;
  }

  public Map<Transaction, Set<Variable>> getTransactionToReadSet() {
    return transactionToReadSet;
  }

  public void setTransactionToReadSet(Map<Transaction, Set<Variable>> transactionToReadSet) {
    this.transactionToReadSet = transactionToReadSet;
  }

  public Map<Transaction, Set<Variable>> getTransactionToWriteSet() {
    return transactionToWriteSet;
  }

  public void setTransactionToWriteSet(Map<Transaction, Set<Variable>> transactionToWriteSet) {
    this.transactionToWriteSet = transactionToWriteSet;
  }

  public Map<Transaction, Integer> getTransactionOperationPointer() {
    return transactionOperationPointer;
  }

  public void setTransactionOperationPointer(Map<Transaction, Integer> transactionOperationPointer) {
    this.transactionOperationPointer = transactionOperationPointer;
  }

  public void run() {
    while (true) {
      List<Transaction> transactions = timeToTransactions.get(systemTime);
      if (transactions != null) {
        ready.addAll(transactions);
      }
      for (Transaction transaction : ready) {
        Set<Variable> read = transactionToReadSet.get(transaction);
        Set<Variable> write = transactionToWriteSet.get(transaction);

        try {
          for (Variable variable : write) {
            if (writeLocks.get(variable) != null) {
              throw new IllegalAccessException();
            }
          }
        } catch (IllegalAccessException e) {
          continue;
        }

        for (Variable variable : write) {
          writeLocks.put(variable, transaction);
        }

        for (Variable variable : read) {
          readLocks.computeIfAbsent(variable, k -> new ArrayList<>());
          readLocks.get(variable).add(transaction);
        }
        System.out.println("All locks for " + transaction);
        running.add(transaction);
      }
      ready.removeAll(running);

      if (running.size() > 0) {
        Transaction thisWillRun = running.get(0);
        System.out.println("This will run " + thisWillRun);

        int currentOperation = transactionOperationPointer.get(thisWillRun);
        System.out.println("This operation will run " + transactionOperations.get(thisWillRun).get(currentOperation));
        Operation operation = transactionOperations.get(thisWillRun).get(currentOperation);

        if (operation.getInstruction().equals("R") && operation.getVariable().getId() == 5) {
          AirlinesManager airlinesManager = new AirlinesManager();
          airlinesManager.get();
        }

        currentOperation++;
        transactionOperationPointer.put(thisWillRun, currentOperation);

        if (currentOperation > transactionOperations.get(thisWillRun).size() - 1) {
          System.out.println("All operations run for " + thisWillRun);
          for (Variable variable : variables) {
            if (writeLocks.get(variable) != null && writeLocks.get(variable) == thisWillRun) {
              writeLocks.put(variable, null);
            }
            if (readLocks.get(variable) != null && readLocks.get(variable).contains(thisWillRun)) {
              readLocks.get(variable).remove(thisWillRun);
            }
          }
          running.remove(thisWillRun);
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
