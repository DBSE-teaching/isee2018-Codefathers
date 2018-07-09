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

import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.models.Tracking;

public class ConfirmTrackingVIewModel extends AndroidViewModel {
    /**
     * the tracking to be created
     */
    private Tracking tracking;
    /**
     * determnines if the tracking is written in the database.
     */
    private MutableLiveData<Boolean> written;

    public ConfirmTrackingVIewModel(@NonNull Application application) {
        super(application);
        written = new MutableLiveData<Boolean>();
    }

    /**
     * returns MutableLiveData that can be listened on the ui and
     * changes to true or false depending on if the tracking is
     * written on the db.
     * @return MutableLiveData
     */
    public MutableLiveData<Boolean> isWritten() {
        return written;
    }

    /**
     * sets the written value
     * @param value
     */
    private void setWritten(Boolean value) {
        if(written == null){
            written = new MutableLiveData<Boolean>();
        }
        this.written.setValue(value);
    }

    /**
     * sets the current tracking to be sent to database
     * @param tracking
     */
    public void setTracking(Tracking tracking) {
        this.tracking = tracking;
    }

    /**
     * writes tracking to db and checks if it is written also notifies the observers that
     * listen to isWritten by changing the written value.
     */
    public void writeTrackingToDb() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("trackings");
        String trackingID = tracking.getCreator();
        dbRef.child(trackingID).setValue(tracking)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        setWritten(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        setWritten(false);
                    }
                });

    }
    public void addSituationLog(LogItem log) {
        if(log.getRecievers() == null && tracking !=null){
            if(tracking.getFollowers() != null)log.setRecievers(tracking.getFollowers());
        };
        if(log.getCreator() == null && tracking != null){
            log.setCreator(tracking.getCreator());
        }
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("logItems");
        String id = dbRef.push().getKey();
        dbRef.child(id).setValue(log);
    }

}
