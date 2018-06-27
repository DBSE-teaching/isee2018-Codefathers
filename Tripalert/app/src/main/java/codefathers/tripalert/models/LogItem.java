package codefathers.tripalert.models;

import java.util.Date;

public class LogItem {
    /**
     * the status that logItem belongs
     * ex: status 0: the tracking has created;
     */
    private int status;
    private String message;
    private Date createdAt;

    public LogItem(int status, String message, Date createdAt) {
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
