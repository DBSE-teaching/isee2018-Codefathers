package codefathers.isee.com.tripalert;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class NotificationService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_TOAST = "codefathers.isee.com.tripalert.action.Toast";
    private static final String ACTION_NOTIFICATION = "codefathers.isee.com.tripalert.action.Notification";

    // TODO: Rename parameters
    private static final String EXTRA_TITLE = "codefathers.isee.com.tripalert.extra.TITLE";
    private static final String EXTRA_BODY = "codefathers.isee.com.tripalert.extra.BODY";

    public NotificationService() {
        super("NotificationService");
    }

    /**
     * Starts this service to perform action Toast with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionToast(Context context, String TITLE, String BODY) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_TOAST);
        intent.putExtra(EXTRA_TITLE, TITLE);
        intent.putExtra(EXTRA_BODY, BODY);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Notification with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


     public static void startActionNotification(Context context, String TITLE, String BODY) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_NOTIFICATION);
        intent.putExtra(EXTRA_TITLE, TITLE);
        intent.putExtra(EXTRA_BODY, BODY);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_TOAST.equals(action)) {
                final String TITLE = intent.getStringExtra(EXTRA_TITLE);
                final String BODY = intent.getStringExtra(EXTRA_BODY);
                handleActionToast(TITLE, BODY);
            } else if (ACTION_NOTIFICATION.equals(action)) {
                final String TITLE = intent.getStringExtra(EXTRA_TITLE);
                final String BODY = intent.getStringExtra(EXTRA_BODY);
                handleActionNotification(TITLE, BODY);
            }
        }
    }

    /**
     * Handle action Toast in the provided background thread with the provided
     * parameters.
     */

   private void handleActionToast(String TITLE, String BODY) {
       try {
            Thread.sleep(10);
           Log.d("Intent nio","Notific intent works");//
           //toast
           Context context = getApplicationContext();
           CharSequence text = "Hello toast hoho!";
           int duration = Toast.LENGTH_SHORT;
           Toast.makeText(this, text, duration).show();

       } catch (InterruptedException e) {
           // Restore interrupt status.
           Log.d("Intent nio","FAILED otific intent");
           Thread.currentThread().interrupt();
       }
   }


 private void handleActionNotification(String TITLE, String BODY) {

    }

    ////simple TOAST

    public static void sendToast(Context ctx, String txt, String body){
        //Context context = getApplicationContext();
        CharSequence text = txt+body;
        int duration = Toast.LENGTH_SHORT;
        String xxx = Toast.makeText(ctx, text, duration).getClass().toString();
        Log.d("action", xxx);
        Toast.makeText(ctx, text, duration).show();

                //.show();
    }
}



