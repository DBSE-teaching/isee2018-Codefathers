package codefathers.tripalert.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import codefathers.tripalert.models.AppUser;

public class SelectConctactsViewModel extends AndroidViewModel {

    private List<AppUser> contacts;
    private MutableLiveData<List<AppUser>> databaseUsers;
    private List<AppUser>filteredContacts;
    private List<AppUser>selectedContacts;
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
        //load here the users as list.
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


    }

    public void addContact(AppUser user){
        if(this.contacts == null){
            this.contacts = new ArrayList<AppUser>();
        }
        this.contacts.add(user);
    }


}
