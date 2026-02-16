package bankLogManager;

import java.util.List;

public interface LogStorage {

    void addLog(LogEntry log);

    List<LogEntry> getLogsByAccount(String accountNumber);

    List<LogEntry> getRecentLogs(int n);

    List<LogEntry> getLogsByAction(ActionType actionType);

    List<LogEntry> getAllLogs();
}
