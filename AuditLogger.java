
package audit;

import java.time.LocalDateTime;
import java.util.*;

public class AuditLogger {

    // Singleton pattern
    private static AuditLogger instance;

    private List<String> logs = new ArrayList<>();

    private AuditLogger() {}

    public static AuditLogger getInstance() {
        if (instance == null)
            instance = new AuditLogger();

        return instance;
    }

    public void log(String status, String message) {
        logs.add(LocalDateTime.now() + " | " +
                status + " | " + message);
    }
//عرض السجلات 
    public void showLogs() {
        for (String s : logs)
            System.out.println(s);
    }
}
