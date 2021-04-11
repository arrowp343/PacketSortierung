package packageSortingCenter.Events;

import csv.PackageType;

public class LogEvent {
    private final LogEventType logEventType;
    private final PackageType packageType;

    public LogEvent(LogEventType logEventType, PackageType packageType) {
        this.logEventType = logEventType;
        this.packageType = packageType;
    }

    public LogEvent(LogEventType logEventType) {
        this(logEventType, null);
    }

    public LogEventType getLogEventType() {
        return logEventType;
    }

    public PackageType getPackageType() {
        return packageType;
    }
}
