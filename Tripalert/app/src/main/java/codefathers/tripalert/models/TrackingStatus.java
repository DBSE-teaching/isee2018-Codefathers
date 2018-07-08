package codefathers.tripalert.models;

public final class TrackingStatus {
    private TrackingStatus () { // private constructor
    }
    public static final int
            STARTED = 0,
            DELAYED = 1,
            FINISHED = 2,
            EMERGENCY = 3,
            NOT_RESPONDING = 4,
            ABORTED = 5;
}
