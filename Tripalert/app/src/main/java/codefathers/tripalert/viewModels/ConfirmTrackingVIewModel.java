package codefathers.tripalert.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import codefathers.tripalert.models.Tracking;

public class ConfirmTrackingVIewModel extends AndroidViewModel {
    private Tracking tracking;
    private MutableLiveData<Boolean> written;

    public ConfirmTrackingVIewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> isWritten() {
        return written;
    }

    private void setWritten(Boolean value) {
        this.written.setValue(value);
    }

    public void setTracking(Tracking tracking) {
        this.tracking = tracking;
    }

    public void writeTrackingToDb() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("trackings");
        String trackingID = tracking.getCreator();
                                                                        //write tracking to db and then check if succees
        dbRef.child(trackingID).setValue(tracking)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                                                                        //notify written valuse
                        setWritten(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                                                                        //notify written value
                        setWritten(false);
                    }
                });

    }
}
