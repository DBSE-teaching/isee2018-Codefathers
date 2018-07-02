package codefathers.tripalert.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import codefathers.tripalert.FireSettings;
import codefathers.tripalert.R;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.services.DatabaseService;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;


public class GoogleLoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private Context context;
    private IRequestListener listener;

    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1001;


    private ImageView profileImage;
    private TextView emailText;
    private TextView statusText;
    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener authListener;
    public static final String GOOGLE_CLIENT_ID = FireSettings.GOOGLE_CLIENT_ID;
    //public static final String GOOGLE_CLIENT_SECRET = FireSettings.GOOGLE_CLIENT_SECRET;


    private static final String TAG = "GoogleAuth";
    private String idToken = null;
    private String userEmail = null;
    public static final String SENDER_ID = FireSettings.SENDER_ID; //Firebase project

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        profileImage = (ImageView) findViewById(R.id.profileImage);
        emailText = (TextView) findViewById(R.id.emailText);
        statusText = (TextView) findViewById(R.id.statusText);

        emailText.setText("");
        statusText.setText("Signed Out");


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
//

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        fbAuth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    emailText.setText(user.getEmail());
                    statusText.setText(getResources().getString(R.string.signedOut));
                    if (user.getPhotoUrl() != null) {
                        displayImage(user.getPhotoUrl());
                    }

                } else {
                    emailText.setText("");
                    statusText.setText(getResources().getString(R.string.signedOut));
                    profileImage.setImageResource(
                            R.drawable.common_google_signin_btn_icon_dark);

                }
            }
        };
    }

    public void signIn(View view) {
        Intent signInIntent =
                Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result =
                    Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                idToken = account.getIdToken();
                userEmail = account.getEmail();
                Log.i(TAG, "moi88Token = " + idToken);
                Log.i(TAG, "moi88Email = " + userEmail);
                AppUser user = new AppUser(userEmail,idToken);
                DatabaseService databaseService = new DatabaseService();
                databaseService.writeUser(user);
                try
                {
                    Thread.sleep(2000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                //MUST DO: REMOVE THE THREAD SLEEP
                Intent nextAct = new Intent(this,SpecifyDetails.class);
                startActivity(nextAct);
            } else {
                Log.d(TAG,"onActivityResult: failed");
            }
        }
    }

    public void signOut(View view) {
        fbAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG,"Google Play Services failure.");
    }

    void displayImage(Uri imageUrl) {
        new DownloadImageTask((ImageView) findViewById(R.id.profileImage))
                .execute(imageUrl.toString());
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        fbAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            fbAuth.removeAuthStateListener(authListener);

        }
    }


}
