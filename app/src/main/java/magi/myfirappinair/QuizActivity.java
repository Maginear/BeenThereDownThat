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
    public static final String GAME_PREFERENCES_AVATAR = "Avatar";
    public static final String GAME_PREFERENCES_SCORE = "Score";
    public static final String GAME_PREFERENCES_CURRENT_QUESTION = "CurQuestion";
    public static final String XML_TAG_QUESTION_BLOCK = "questions";
    public static final String XML_TAG_QUESTION = "question";
    public static final String XML_TAG_QUESTION_ATTRIBUTE_NUMBER = "number";
    public static final String XML_TAG_QUESTION_ATTRIBUTE_TEXT = "text";
    public static final String XML_TAG_QUESTION_ATTRIBUTE_IMAGEURL = "imageUrl";
    public static final String DEBUG_TAG = "MyActivity Preferences";

    public static final int QUESTION_BATCH_SIZE = 15;
    public static final int TAKE_AVATAR_CAMERA_REQUEST = 1;
    public static final int TAKE_AVATAR_GALLERY_REQUEST = 2;

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
