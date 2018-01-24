package transactionmanager;

import airlines.AirlinesManager;
import airlines.Flight;

import java.util.*;

public class TransactionManager {

  private List<Transaction> transactions;
  private Set<Variable> variables;
  private int systemTime = 0;
  private List<Transaction> ready = new ArrayList<>();
  private List<Transaction> running = new ArrayList<>();
  private List<Transaction> done = new ArrayList<>();
  private Map<Variable, Transaction> writeLocks = new HashMap<>();
  private Map<Variable, List<Transaction>> readLocks = new HashMap<>();

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public Set<Variable> getVariables() {
    return variables;
  }

  public void setVariables(Set<Variable> variables) {
    this.variables = variables;
  }

  public void run() {
    ready.addAll(transactions);
    while (true) {
      for (Transaction transaction : ready) {
        Set<Variable> read = transaction.getReadSet();
        Set<Variable> write = transaction.getWriteSet();

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
        System.out.println("All locks for " + transaction + " acquired at " + systemTime);
        running.add(transaction);
      }
      ready.removeAll(running);

      if (running.size() > 0) {
        Transaction thisWillRun = running.get(0);
        System.out.println("This will run " + thisWillRun + " at " + systemTime);

        int currentOperation = thisWillRun.getOperationPointer();
        System.out.println("This operation will run " + thisWillRun.getOperations().get(currentOperation));
        Operation operation = thisWillRun.getOperations().get(currentOperation);

        if (operation.getInstruction().equals("R") && operation.getVariable().getId().equals("flights")) {
          AirlinesManager airlinesManager = new AirlinesManager();
          List<Flight> flights = airlinesManager.getAllFlights();
          System.out.println(flights);
          airlinesManager.close();
        }

        currentOperation++;
        thisWillRun.setOperationPointer(currentOperation);

        if (currentOperation > thisWillRun.getOperations().size() - 1) {
          System.out.println("All operations run for " + thisWillRun + " at " + systemTime);
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
      if (done.size() == transactions.size()) {
        break;
      }
      systemTime++;
    }
  }
}
