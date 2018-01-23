import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

  private static final String FILENAME = "transactions";

  public static void main(String[] args) {

    try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

      String sCurrentLine = br.readLine();
      int noTransactions = Integer.parseInt(sCurrentLine);
      Set<Variable> variables = new HashSet<>();
      Map<Transaction, List<Operation>> transactionOperations = new HashMap<>();
      Map<Transaction, Set<Variable>> transactionToReadSet = new HashMap<>();
      Map<Transaction, Set<Variable>> transactionToWriteSet = new HashMap<>();
      Map<Transaction, Integer> transactionTimestamps = new HashMap<>();
      Map<Transaction, Integer> transactionOperationPointer = new HashMap<>();
      Map<Integer, List<Transaction>> timeToTransactions = new HashMap<>();

      for (int noTransaction = 0; noTransaction < noTransactions; noTransaction++) {
        Transaction transaction = new Transaction(noTransaction);
        transactionOperationPointer.put(transaction, 0);
        sCurrentLine = br.readLine();
        int noInstructions = Integer.parseInt(sCurrentLine.split(" ")[0]);
        int nTime = Integer.parseInt(sCurrentLine.split(" ")[1]);

        timeToTransactions.computeIfAbsent(nTime, k -> new ArrayList<>());
        timeToTransactions.get(nTime).add(transaction);

        transactionTimestamps.put(transaction, nTime);
        for (int noInstruction = 0; noInstruction < noInstructions; noInstruction++) {
          sCurrentLine = br.readLine();
          String instruction = sCurrentLine.split(" ")[0];
          int var = Integer.parseInt(sCurrentLine.split(" ")[1]);
          Variable variable = new Variable(var);
          variables.add(variable);
          transactionOperations.computeIfAbsent(transaction, k -> new ArrayList<>());
          transactionOperations.get(transaction).add(new Operation(instruction, variable));
        }
      }
      for (Transaction transaction : transactionOperations.keySet()) {
        System.out.println(transaction + " " + transactionOperations.get(transaction));
      }

      for (Transaction transaction : transactionOperations.keySet()) {
        List<Operation> operations = transactionOperations.get(transaction);
        Set<Variable> readSet = new HashSet<>();
        Set<Variable> writeSet = new HashSet<>();

        for (Operation operation : operations) {
          if (operation.getInstruction().equals("W")) {
            writeSet.add(operation.getVariable());
          } else if (operation.getInstruction().equals("R")) {
            readSet.add(operation.getVariable());
          }
        }
        transactionToReadSet.put(transaction, readSet);
        transactionToWriteSet.put(transaction, writeSet);
      }

      System.out.println(transactionToReadSet);
      System.out.println(transactionToWriteSet);

      TransactionManager transactionManager = new TransactionManager();
      transactionManager.setVariables(variables);
      transactionManager.setTransactionOperations(transactionOperations);
      transactionManager.setTransactionToReadSet(transactionToReadSet);
      transactionManager.setTransactionToWriteSet(transactionToWriteSet);
      transactionManager.setTransactionTimestamps(transactionTimestamps);
      transactionManager.setTransactionOperationPointer(transactionOperationPointer);
      transactionManager.setTimeToTransactions(timeToTransactions);
      transactionManager.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
