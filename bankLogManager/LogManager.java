package bankLogManager;

import java.util.*;

public class LogManager implements LogStorage {

    private final List<LogEntry> allLogs = new ArrayList<>();
    private final Map<String, List<LogEntry>> accountLogs = new HashMap<>();
    private final Map<ActionType, List<LogEntry>> actionLogs = new EnumMap<>(ActionType.class);
    private long nextLogId = 1;
    private final SuspiciousDetector detector = new SuspiciousDetector();

    public LogManager() {
        for (ActionType type : ActionType.values()) {
            actionLogs.put(type, new ArrayList<>());
        }
    }

    public void addLog(String accountNumber,
                       ActionType actionType,
                       double amount,
                       Status status) {

        LogEntry log = new LogEntry(nextLogId++, accountNumber,
                actionType, amount, status);

        addLog(log);
    }

    @Override
    public void addLog(LogEntry log) {
        allLogs.add(log);

        accountLogs
                .computeIfAbsent(log.getAccountNumber(),
                        k -> new ArrayList<>())
                .add(log);

        actionLogs.get(log.getActionType()).add(log);
    }

    @Override
    public List<LogEntry> getLogsByAccount(String accountNumber) {
        return new ArrayList<>(
                accountLogs.getOrDefault(accountNumber,
                        Collections.emptyList()));
    }

    @Override
    public List<LogEntry> getRecentLogs(int n) {

        int size = Math.min(n, allLogs.size());

        List<LogEntry> recent =
                new ArrayList<>(
                        allLogs.subList(allLogs.size() - size,
                                allLogs.size()));

        Collections.reverse(recent);
        return recent;
    }

    @Override
    public List<LogEntry> getLogsByAction(ActionType actionType) {
        return new ArrayList<>(
                actionLogs.getOrDefault(actionType,
                        Collections.emptyList()));
    }

    @Override
    public List<LogEntry> getAllLogs() {
        return new ArrayList<>(allLogs);
    }

    public List<LogEntry> getSuspiciousLogs() {
        return detector.detectSuspiciousLogs(this);
    }
}
