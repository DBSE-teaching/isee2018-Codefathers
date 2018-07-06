package codefathers.tripalert.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.services.DatabaseService;

public class SelectConctactsViewModel extends AndroidViewModel {
    String TAG = "Select Contacts View Model";
    private List<AppUser> contacts;
    private MutableLiveData<List<AppUser>> databaseUsers;
    private List<AppUser>filteredContacts;
    private List<AppUser>selectedContacts;
    private DatabaseService databaseService;
    public SelectConctactsViewModel(@NonNull Application application ) {
        super(application);
    }

    public List<AppUser> getFilteredContacts (List<AppUser> databaseUsers) {

        for( AppUser contact : contacts){
            if (databaseUsers.contains(contact)){
                addToFilterContacts(contact);
            };
        }
        return filteredContacts;
    }

    private void addToFilterContacts(AppUser contact){
        if (filteredContacts == null){
            filteredContacts = new ArrayList<AppUser>();
        }
        filteredContacts.add(contact);
    }

    public List<AppUser> getSelectedContacts(){
     return selectedContacts;
    }

    public boolean addRemoveSelectedContact(AppUser contact) {
        if (selectedContacts == null) {
            selectedContacts = new ArrayList<AppUser>();
        }
        if(selectedContacts.contains(contact)){
            this.selectedContacts.remove(contact);
            return false;
        }else{
            this.selectedContacts.add(contact);
            return true;
        }
    }

    public MutableLiveData<List<AppUser>> getDatabaseUsers(){

        if(databaseUsers == null){
            databaseUsers = new MutableLiveData <List<AppUser>>();
            loadDatabaseUsers();
        }
        return databaseUsers;
    }

    private void loadDatabaseUsers(){
        /*todo : [ALABA] implement firebase logic to fetch Users
         */
        List<AppUser> userList = new ArrayList<>();
        DatabaseReference dbRef;
        databaseService = new DatabaseService();

        dbRef = FirebaseDatabase.getInstance().getReference("users");

            Query userQuery = dbRef;
            userQuery.addListenerForSingleValueEvent( new ValueEventListener() {
                //List<AppUser> userList = new ArrayList<>();
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "FETCHING Users ");
                    for (DataSnapshot uSnapshot: dataSnapshot.getChildren()) {
                        AppUser user = uSnapshot.getValue(AppUser.class);
                        userList.add(user);
                        Log.d(TAG, user.getPhoneNumber());
                    }
                    Log.d(TAG, "DONE ");

                //TODO   passing userList to LiveData<databaseUsers>
                    // databaseUsers.setValue(userList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadUser:onCancelled", databaseError.toException());

                    // ...
                }


            });


    /*

        List<AppUser> userList = new ArrayList<AppUser>();
        userList.add(new AppUser("+306943864227"));
        userList.add(new AppUser("+4915251956592"));
        userList.add(new AppUser("+306972834844"));
        userList.add(new AppUser("+4915730263379"));
        userList.add(new AppUser("444444444"));
        userList.add(new AppUser("555555555"));
        userList.add(new AppUser("666666666"));
        userList.add(new AppUser("777777777"));
        //then just pass the data here whenever it changes
        databaseUsers.setValue(userList);

*/
   }

    public void addContact(AppUser user){
        if(this.contacts == null){
            this.contacts = new ArrayList<AppUser>();
        }
        this.contacts.add(user);
    }


}
