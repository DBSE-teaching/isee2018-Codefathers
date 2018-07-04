package codefathers.tripalert.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import codefathers.tripalert.models.AppUser;

public class SelectConctactsViewModel extends AndroidViewModel {

    private MutableLiveData<List<AppUser>> databaseUsers;
    public SelectConctactsViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<List<AppUser>> getDatabaseUsers(){
        if(databaseUsers == null){
            databaseUsers = new MutableLiveData <List<AppUser>>();
            loadDatabaseUsers();
        }
        return databaseUsers;
    }

    private void loadDatabaseUsers(){
        //todo : implement firebase logic to fetch Users

        //load here the users.
        List<AppUser> userList = new ArrayList<AppUser>();
        databaseUsers.setValue(userList);

    }


}
