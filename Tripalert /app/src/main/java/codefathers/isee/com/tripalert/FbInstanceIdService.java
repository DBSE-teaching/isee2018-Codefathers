package codefathers.isee.com.tripalert;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

//the class extending FirebaseInstanceIdService
public class FbInstanceIdService extends FirebaseInstanceIdService {

    //when the token is generated
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MyRefreshedToken", refreshedToken);
       // PreferenceData.setStringPref(PreferenceData.KEY_GCM_ID,this,refreshedToken);
        // Save token to database
     sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}