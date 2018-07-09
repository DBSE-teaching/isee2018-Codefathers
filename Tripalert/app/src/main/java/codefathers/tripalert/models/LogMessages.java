package codefathers.tripalert.models;

public final class LogMessages {

    private LogMessages(){

    }

    public static String getEmergency(String location){
        String KnownLocation = location != null? "his last known location was"+location : "The user does not have location sharing enabled";
        return "this contact is on an emergency situation, please contact with him. "+KnownLocation;
    }

    public static  String getOnStart( String From, String To, int estimatedTime) {
        return  "...has started a tracking. He will go from " + From + " to " + To + "." +
                "The trip will take around " + Integer.toString(estimatedTime)+ " minutes";
    }
    public static String getOnNotResponding(){
        return "... has stopped moving for an extensive time period, maybe reach out to him?";
    }
    public static String delayed(){
        return "... has not arrived on estimated time.";
    }
    public static String onResume(){
        return "... has started moving";
    }
    public static String onEstimateAgain(int estimatedTime){
        return "... the trip will take "+estimatedTime+" more minutes";
    }
    public static String onArrived(String address){
        return "... has arrived to "+address+" safely";
    }
    public static String onAbort(String reason){
        return "... the track was aborted. Reason of abortion "+reason;
    };
    public static String onUnfollow(){
        return "... is no longer following your tracking.";
    }

}
