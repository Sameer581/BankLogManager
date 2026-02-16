package bankLogManager;

import java.util.ArrayList;
import java.util.List;

public class SuspiciousDetector {

    public List<LogEntry> detectSuspiciousLogs(LogStorage storage) {

        List<LogEntry> suspicious = new ArrayList<>();

        for (LogEntry log : storage.getAllLogs()) {

            // Rule 1: Large Withdrawal
            if (log.getActionType() == ActionType.WITHDRAW &&
                log.getAmount() > 50000) {
                suspicious.add(log);
            }

            // Rule 2: More than 3 FAILED_LOGIN in last 5 logs
            if (log.getActionType() == ActionType.FAILED_LOGIN) {

                List<LogEntry> accountLogs =
                        storage.getLogsByAccount(log.getAccountNumber());

                int size = accountLogs.size();
                int start = Math.max(0, size - 5);

                int failedCount = 0;

                for (int i = start; i < size; i++) {
                    if (accountLogs.get(i).getActionType()
                            == ActionType.FAILED_LOGIN) {
                        failedCount++;
                    }
                }

                if (failedCount > 3) {
                    suspicious.add(log);
                }
            }
        }

        return suspicious;
    }
}
