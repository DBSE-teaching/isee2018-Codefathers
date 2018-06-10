package codefathers.tripalert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //replace the bar with the title with the main menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //actions for menu
        switch (item.getItemId()){
            case R.id.settingsMenu:
                //this is how we change from one screen to another)
                Intent intent1 = new Intent(this,Settings.class);
                this.startActivity(intent1);
                return  true;
            case R.id.aboutMenu:
                Intent intent2 = new Intent(this,About.class);
                this.startActivity(intent2);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
