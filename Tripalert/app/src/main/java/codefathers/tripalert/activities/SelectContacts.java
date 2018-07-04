package codefathers.tripalert.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.services.DatabaseService;
import codefathers.tripalert.viewModels.HomeScreenViewModel;
import codefathers.tripalert.viewModels.SelectConctactsViewModel;

public class SelectContacts extends AppCompatActivity implements NextStepActivity{

    private Tracking tracking;
    private SelectConctactsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        viewModel = ViewModelProviders.of(this).get(SelectConctactsViewModel.class);
        getData();
        setContacts();
        viewModel.getDatabaseUsers().observe(this, new Observer<List<AppUser>>() {
            @Override
            public void onChanged(@Nullable List<AppUser> appUsers) {
                createAppUserView(viewModel.getFilteredContacts(appUsers));
            }

        });

    }
    @Override
    public void onNext(View v) {
        AppUser user3 = new AppUser("3333333333");
        AppUser user4 = new AppUser("5555555555");
        AppUser user5 = new AppUser("2222222222");
        onClickContact(user3);
        onClickContact(user4);
        onClickContact(user3);
        setFollowers();
        if(tracking.getCreator().getFollowers() != null){

            Intent intent = new Intent(SelectContacts.this,ConfirmTracking.class);
            intent.putExtras(getBundle());
            startActivity(intent);
        }else{
            String msg = getString(R.string.cannotProcceedFollowers);
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("tracking",tracking);
        return bundle;
    }

    @Override
    public void onCancel(View v) {
        startActivity(new Intent(SelectContacts.this,HomeScreen.class));
    }

    @Override
    public void getData() {
        tracking = (Tracking)getIntent().getSerializableExtra("tracking");
    }

    @Override
    public void saveData() {

    }

    @Override
    public void onBackPressed()
    {
        String msg = getString(R.string.cannotProcceedGoBack);
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setFollowers() {
        this.tracking.getCreator().setFollowers(viewModel.getSelectedContacts());
    }

    private void setContacts(){
        /*TODO: [ILIAS] create here the logic behind getting the contacts
            make sure that for each contact, an AppUser object is created and the
            viewmodel.addContact is called. so perhaps u need to wrap it into
            a foreach loop, or whatever :P

            EDO FORTONEI TIS EPAFES PO TO KINITO

         */

        //sou afhsa mia endiktikh eggrafh

        AppUser user3 = new AppUser("3333333333");
        user3.setUserName("prits");
        viewModel.addContact(user3);
    }

    private void createAppUserView(List<AppUser> contacts ){
        /* TODO:[ILIAS] create the logic behind displaying the contacts
            here define any logic in order to create the listview (or whatever u
            choose) for the displayed contacts, you dont need to worry about
            filtering the contacts, or how you get this contacts list.
         */

    }

    /* TODO: [ILIAS] use this to remove or add a selected contact
     *
     * //if you want to see the selected contacts call viewmodel.getSelectedContacts
     * */
    private boolean onClickContact(AppUser contact){
        return this.viewModel.addRemoveSelectedContact(contact);
    }

}
