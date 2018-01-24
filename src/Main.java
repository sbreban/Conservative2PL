import transactionmanager.*;

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
      List<Transaction> transactions = new ArrayList<>();

      for (int noTransaction = 0; noTransaction < noTransactions; noTransaction++) {
        sCurrentLine = br.readLine();
        int noInstructions = Integer.parseInt(sCurrentLine.split(" ")[0]);
        String transactionId = sCurrentLine.split(" ")[1];

        Transaction transaction = new Transaction(transactionId);
        transaction.setOperations(new ArrayList<>());
        transactions.add(transaction);

        for (int noInstruction = 0; noInstruction < noInstructions; noInstruction++) {
          sCurrentLine = br.readLine();
          String[] lineElements = sCurrentLine.split(" ");
          String instruction = lineElements[0];
          String var = lineElements[1];

          Variable variable = new Variable(var);
          variables.add(variable);
          Operation operation = new Operation(instruction, variable);
          if (lineElements.length > 2) {
            List<String> parameters = new ArrayList<>();
            for (int parameterIndex = 2; parameterIndex < lineElements.length; parameterIndex++) {
              parameters.add(lineElements[parameterIndex]);
            }
            operation.setParameters(new OperationParameters(parameters));
          }

          transaction.getOperations().add(operation);
        }
      }

      for (Transaction transaction : transactions) {
        List<Operation> operations = transaction.getOperations();
        Set<Variable> readSet = new HashSet<>();
        Set<Variable> writeSet = new HashSet<>();

        for (Operation operation : operations) {
          if (operation.getInstruction().equals("W")) {
            writeSet.add(operation.getVariable());
          } else if (operation.getInstruction().equals("R")) {
            readSet.add(operation.getVariable());
          }
        }
        transaction.setReadSet(readSet);
        transaction.setWriteSet(writeSet);
      }

      for (Transaction transaction : transactions) {
        System.out.println(transaction + " " + transaction.getOperations());
      }

      TransactionManager transactionManager = new TransactionManager();
      transactionManager.setTransactions(transactions);
      transactionManager.setVariables(variables);
      transactionManager.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
