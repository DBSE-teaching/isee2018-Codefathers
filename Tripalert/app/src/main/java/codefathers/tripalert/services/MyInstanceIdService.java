package codefathers.tripalert.services;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import codefathers.tripalert.models.AppUser;
//the class extending FirebaseInstanceIdService

public class MyInstanceIdService extends FirebaseInstanceIdService {
 String TAG = "MyInstanceIdService";
 DatabaseService databaseService;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MyRefreshedToken", refreshedToken);
       // PreferenceData.setStringPref(PreferenceData.KEY_GCM_ID,this,refreshedToken);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseService = new DatabaseService();
        //FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        if (currentUser != null) {
            //String userName = currentUser.getDisplayName();
            String userEmail = currentUser.getEmail();
            //String userId = currentUser.getUid();

            databaseService.dbWriteToken(userEmail, refreshedToken);
            Log.d(TAG, "User data saved");

                 } else {

            Log.d(TAG, "USER NOT SIGNED IN, hence no data saved");

            // No user is signed in.
        }
    }


}