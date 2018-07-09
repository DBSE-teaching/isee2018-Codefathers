package codefathers.tripalert.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@IgnoreExtraProperties
public class LogItem implements Serializable {
    /**
     * the status that logItem belongs
     * ex: status 0: the tracking has created;
     */
    private int status;
    private String message;
    private String createdAt;

    public LogItem(){

    }
    public LogItem(int status, String message) {
        this.status = status;
        this.message = message;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.GERMANY);
        this.createdAt = formatter.format(new Date());
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

}
