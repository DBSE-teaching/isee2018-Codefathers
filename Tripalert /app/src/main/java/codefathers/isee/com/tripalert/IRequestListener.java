package codefathers.isee.com.tripalert;
/**
 * Request Listener Interface. It is just to handle the HTTP request error
 */

public interface IRequestListener {

    void onComplete();

    void onError(String message);
}
