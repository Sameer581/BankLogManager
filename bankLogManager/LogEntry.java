package bankLogManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final long logId;
    private final String accountNumber;
    private final ActionType actionType;
    private final double amount;
    private final LocalDateTime timestamp;
    private final Status status;

    public LogEntry(long logId, String accountNumber,
                    ActionType actionType,
                    double amount, Status status) {

        if (accountNumber == null || accountNumber.isBlank())
            throw new IllegalArgumentException("Account number required");

        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative");

        this.logId = logId;
        this.accountNumber = accountNumber;
        this.actionType = actionType;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    public long getLogId() { return logId; }
    public String getAccountNumber() { return accountNumber; }
    public ActionType getActionType() { return actionType; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Status getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("[ID:%d] %s | %s | %.2f | %s | %s",
                logId,
                accountNumber,
                actionType,
                amount,
                status,
                timestamp.format(FORMATTER));
    }
}
