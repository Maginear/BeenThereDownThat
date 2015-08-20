package magi.myfirappinair;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Date;


public class QuizActivity extends ActionBarActivity {

    public static final String GAME_PREFERENCES = "GamePrefs";
    public static final String LASTLAUNCH = new Date().toString();
    public static final String GAME_PREFERENCES_NICKNAME = "Nickname";
    public static final String GAME_PREFERENCES_EMAIL = "Email";
    public static final String GAME_PREFERENCES_PASSWORD = "Password";
    public static final String GAME_PREFERENCES_DOB = "DOB";
    public static final String GAME_PREFERENCES_GENDER = "Gender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_splash);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
