package bankLogManager;

import java.util.List;
import java.util.Scanner;

public class BankingLogManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final LogManager logManager = new LogManager();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== Secure Banking Log Manager ===");
            System.out.println("1. Add Log");
            System.out.println("2. Get Logs by Account");
            System.out.println("3. Get Recent Logs");
            System.out.println("4. Detect Suspicious Activity");
            System.out.println("5. Search by Action");
            System.out.println("6. Exit");

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addLog();
                case 2 -> getByAccount();
                case 3 -> getRecent();
                case 4 -> detectSuspicious();
                case 5 -> searchByAction();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void addLog() {

        System.out.print("Account Number: ");
        String account = scanner.nextLine();

        System.out.print("Action (DEPOSIT/WITHDRAW/TRANSFER/LOGIN/FAILED_LOGIN): ");
        ActionType action =
                ActionType.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Amount: ");
        double amount =
                Double.parseDouble(scanner.nextLine());

        System.out.print("Status (SUCCESS/FAILED): ");
        Status status =
                Status.valueOf(scanner.nextLine().toUpperCase());

        logManager.addLog(account, action, amount, status);

        System.out.println("Log Added Successfully!");
    }

    private static void getByAccount() {
        System.out.print("Account Number: ");
        String account = scanner.nextLine();

        List<LogEntry> logs =
                logManager.getLogsByAccount(account);

        logs.forEach(System.out::println);
    }

    private static void getRecent() {
        System.out.print("How many recent logs: ");
        int n = Integer.parseInt(scanner.nextLine());

        logManager.getRecentLogs(n)
                .forEach(System.out::println);
    }

    private static void detectSuspicious() {
        logManager.getSuspiciousLogs()
                .forEach(System.out::println);
    }

    private static void searchByAction() {
        System.out.print("Action Type: ");
        ActionType action =
                ActionType.valueOf(scanner.nextLine().toUpperCase());

        logManager.getLogsByAction(action)
                .forEach(System.out::println);
    }
}
