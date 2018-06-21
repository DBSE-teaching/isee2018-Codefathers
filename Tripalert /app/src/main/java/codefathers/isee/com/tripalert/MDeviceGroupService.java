package codefathers.isee.com.tripalert;


import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDeviceGroupService {


    private static final String TAG = "tokenService";

    private Context context;
    private IRequestListener listener;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference("tripAlert");
    DatabaseReference tokensRef = mDatabase.child("tokens");
    DatabaseReference messageGroupRef = mDatabase.child("MessageGroup");
    DatabaseReference messagesRef = mDatabase.child("Messages");

    public MDeviceGroupService(Context context, IRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }



    public List<String> setGroup() {

        String token1 = FireSettings.TOKEN_MOTO;
        String token2 = FireSettings.TOKEN_N27A;
        String token3 = FireSettings.TOKEN_N26B;
        String token4 = FireSettings.TOKEN_N26A;

        List<String>receivers = new ArrayList <>();
        receivers.add(token1);
        receivers.add(token2);
        receivers.add(token3);
        receivers.add(token4);

        MessageGroup messageGroup = new MessageGroup();
            messageGroup.Followers = receivers;

          //DatabaseReference tokensRef = mDatabase.child("tokenS");
       //DatabaseReference tokensRef = mDatabase.child("tokensWW");

        Map<String, List> track = new HashMap<>();
        track.put("token111",receivers);
        track.put("token222", receivers);
        messageGroupRef.setValue(track);

        tokensRef.setValue(track);
        return receivers;
    }
    /*

    public void updatetoken(String email, String token) {
        String token = new token(token, email);
        Map<String, Object> tokenUpdates = new HashMap<>();
        tokenUpdates.put("token111xm", new token(email, token));
        tokenUpdates.put("token222xm", new token(email, token));

        tokensRef.updateChildren(tokenUpdates);
    }*/
}
