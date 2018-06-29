package codefathers.tripalert.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.User;

public class SelectContacts extends AppCompatActivity implements NextStepActivity{

    private Tracking tracking;
    private List<User> followers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        tracking = (Tracking)getIntent().getSerializableExtra("tracking");
    }
    @Override
    public void onNext(View v) {

        Intent intent = new Intent(SelectContacts.this,ConfirmTracking.class);
        List <User> userList = new ArrayList<User>();
        userList.add(new User("6948231245","Mitsaros","mitsos@gmail.com"));
        userList.add(new User("6948261245","Mitsaros3","mitso2s@gmail.com"));
        Bundle bundle = new Bundle();
        tracking.getCreator().setFollowers(userList);
        bundle.putSerializable("tracking",tracking);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onCancel(View v) {
        startActivity(new Intent(SelectContacts.this,HomeScreen.class));

    }

    @Override
    public void getData() {

    }

    @Override
    public void saveData() {

    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

}
