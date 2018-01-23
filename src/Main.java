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
      List<Integer> transactionPointer = new ArrayList<>(noTransactions);
      Set<Integer> variables = new HashSet<>();
      Map<Integer, List<Operation>> transactionOperations = new HashMap<>();
      Map<Integer, Integer> transactionTimestamps = new HashMap<>();
      Map<Integer, List<Integer>> timeToTransactions = new HashMap<>();
      Map<Integer, Integer> transactionOperationPointer = new HashMap<>();

      for (int noTransaction = 0; noTransaction < noTransactions; noTransaction++) {
        transactionOperationPointer.put(noTransaction, 0);
        sCurrentLine = br.readLine();
        int noInstructions = Integer.parseInt(sCurrentLine.split(" ")[0]);
        int nTime = Integer.parseInt(sCurrentLine.split(" ")[1]);

        timeToTransactions.computeIfAbsent(nTime, k -> new ArrayList<>());
        timeToTransactions.get(nTime).add(noTransaction);

        transactionTimestamps.put(noTransaction, nTime);
        for (int noInstruction = 0; noInstruction < noInstructions; noInstruction++) {
          sCurrentLine = br.readLine();
          String instruction = sCurrentLine.split(" ")[0];
          int var = Integer.parseInt(sCurrentLine.split(" ")[1]);
          variables.add(var);
          transactionOperations.computeIfAbsent(noTransaction, k -> new ArrayList<>());
          transactionOperations.get(noTransaction).add(new Operation(instruction, var));
        }
      }
      for (Integer transaction : transactionOperations.keySet()) {
        System.out.println(transaction + " " + transactionOperations.get(transaction));
      }

      Map<Integer, Set<Integer>> transactionToReadSet = new HashMap<>();
      Map<Integer, Set<Integer>> transactionToWriteSet = new HashMap<>();
      for (int transaction : transactionOperations.keySet()) {
        List<Operation> operations = transactionOperations.get(transaction);
        Set<Integer> readSet = new HashSet<>();
        Set<Integer> writeSet = new HashSet<>();

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
      transactionManager.setTimeToTransactions(timeToTransactions);
      transactionManager.setTransactionOperations(transactionOperations);
      transactionManager.setTransactionPointer(transactionPointer);
      transactionManager.setTransactionTimestamps(transactionTimestamps);
      transactionManager.setTransactionToReadSet(transactionToReadSet);
      transactionManager.setTransactionToWriteSet(transactionToWriteSet);
      transactionManager.setTransactionOperationPointer(transactionOperationPointer);
      transactionManager.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
