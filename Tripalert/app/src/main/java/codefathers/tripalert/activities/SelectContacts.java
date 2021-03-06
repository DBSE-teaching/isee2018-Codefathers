package codefathers.tripalert.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codefathers.tripalert.R;
import codefathers.tripalert.adapters.ContactsAdapter;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.services.DatabaseService;
import codefathers.tripalert.viewModels.HomeScreenViewModel;
import codefathers.tripalert.viewModels.SelectConctactsViewModel;

public class SelectContacts extends AppCompatActivity implements NextStepActivity{

    private Tracking tracking;
    private SelectConctactsViewModel viewModel;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

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

                List<AppUser> filteredContacts = viewModel.getFilteredContacts(appUsers);
                if(filteredContacts != null ){
                    createAppUserView(filteredContacts);
                }else{
                    onNoFilteredContacts();
                }
            }

        });

    }
    @Override
    public void onNext(View v) {
        setFollowers();
        if(tracking.getFollowers() != null ){
            if(tracking.getFollowers().size() != 0){
                Intent intent = new Intent(SelectContacts.this,ConfirmTracking.class);
                intent.putExtras(getBundle());
                startActivity(intent);
            }else{
                String msg = getString(R.string.cannotProcceedFollowers);
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                toast.show();
            }

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

    /**
     * converts the List of followers into a HashMap in order to send to Firebase.
     */
    private void setFollowers() {
        Map<String,Boolean> map = new HashMap<>();
        if(viewModel.getSelectedContacts()!= null ){
            for (AppUser user : viewModel.getSelectedContacts()) map.put(user.getPhoneNumber(),true);
            this.tracking.setFollowers(map);
        }
    }

    private void setContacts(){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if(hasPhoneNumber > 0)
                {
                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[] {id}, null);
                    while(cursor2.moveToNext())
                    {
                        String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        AppUser user = new AppUser(phoneNumber.replaceAll("[\\s\\-()]", ""));
                        user.setUserName(name);
                        user.setChecked(false);
                        viewModel.addContact(user);
                    }
                    cursor2.close();
                }
            }
        }
        cursor.close();

    }

    private void onNoFilteredContacts(){
     findViewById(R.id.contacts).setVisibility(View.INVISIBLE);
     findViewById(R.id.noContacts).setVisibility(View.VISIBLE);

    }

    private void createAppUserView(List<AppUser> contacts ){
        ListView listView = (ListView) findViewById(R.id.lvContacts);
        final ContactsAdapter adapter = new ContactsAdapter(this,contacts,false);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AppUser contact = contacts.get(i);
                boolean isChecked = onClickContact(contact);
                contact.setChecked(isChecked);

                contacts.set(i,contact);
                adapter.updateRecords(contacts);

            }
        });

    }

    private boolean onClickContact(AppUser contact){
        return this.viewModel.addRemoveSelectedContact(contact);
    }

}
