package magi.myfirappinair;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.prefs.PreferenceChangeEvent;


public class QuizSettingsActivity extends QuizActivity {


    private static final String DEBUG_TAG = "MyActivity Preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_settings);



        Spinner spinner = (Spinner) findViewById(R.id.Spinner_Gender);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences mGameSettings = getSharedPreferences(
                        GAME_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mGameSettings.edit();
                editor.putInt(GAME_PREFERENCES_GENDER, position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(2);

        readPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_settings, menu);
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

    public void onPickDateButtonClick(View view){
        Toast.makeText(QuizSettingsActivity.this, "TODO: launch DataPickDialog",
                Toast.LENGTH_LONG).show();
    }

    public void onSetPasswordButtonClick(View view){
        Toast.makeText(QuizSettingsActivity.this, "TODO: Launch Password Dialog",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences mGameSettings = getSharedPreferences(
                GAME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mGameSettings.edit();

        //TODO get the contents of all preferences and save them
        final EditText nicknameText = (EditText) findViewById(R.id.EditText_Nickname);
        final EditText emailText = (EditText) findViewById(R.id.EditText_Email);
        String strNickname = nicknameText.getText().toString();
        String strEmail = emailText.getText().toString();

        editor.putString(GAME_PREFERENCES_EMAIL, strEmail);
        editor.apply();
        editor.putString(GAME_PREFERENCES_NICKNAME, strNickname);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences mGameSettings = getSharedPreferences(
                GAME_PREFERENCES, Context.MODE_PRIVATE);

        Log.d(DEBUG_TAG, "SHARED PREFERENCES");
        Log.d(DEBUG_TAG, "Nickname is: " + mGameSettings.getString(
                GAME_PREFERENCES_NICKNAME, "Not set"));
        Log.d(DEBUG_TAG, "Email is: " + mGameSettings.getString(
                GAME_PREFERENCES_EMAIL, "Not set"));
        Log.d(DEBUG_TAG, "Gender (M=1, F=2, U=0) is: " + mGameSettings.getInt(
                GAME_PREFERENCES_GENDER, 0));
        //We are not saving the password yet
        Log.d(DEBUG_TAG, "Password is: " + mGameSettings.getString(
                GAME_PREFERENCES_PASSWORD, "Not set"));
        Log.d(DEBUG_TAG, "DOB is: " + android.text.format.DateFormat.format(
                "MMMM dd, yyyy", mGameSettings.getLong(GAME_PREFERENCES_DOB, 0)));
    }

    public void readPreferences(){
        SharedPreferences mGameSettings = getSharedPreferences(
                GAME_PREFERENCES, Context.MODE_PRIVATE);

        final EditText nicknameText = (EditText) findViewById(R.id.EditText_Nickname);
        final EditText emailText = (EditText) findViewById(R.id.EditText_Email);
        final TextView passwordText = (TextView) findViewById(R.id.TextViewPassword);
        final TextView birthDateText = (TextView) findViewById(R.id.TextViewBirthDate);
        final Spinner genderSpinner = (Spinner) findViewById(R.id.Spinner_Gender);

        if (mGameSettings.contains(GAME_PREFERENCES_NICKNAME)) {
            nicknameText.setText(mGameSettings.getString(GAME_PREFERENCES_NICKNAME, ""));
        }
        if (mGameSettings.contains(GAME_PREFERENCES_EMAIL)) {
            emailText.setText(mGameSettings.getString(GAME_PREFERENCES_EMAIL, ""));
        }
        if (mGameSettings.contains(GAME_PREFERENCES_PASSWORD)) {
            passwordText.setText(mGameSettings.getString(GAME_PREFERENCES_NICKNAME, ""));
        }
        if (mGameSettings.contains(GAME_PREFERENCES_DOB)) {
            birthDateText.setText(Long.toString(mGameSettings.getLong(GAME_PREFERENCES_DOB, 0)));
        }
        if (mGameSettings.contains(GAME_PREFERENCES_GENDER)) {
            genderSpinner.setSelection(mGameSettings.getInt(GAME_PREFERENCES_GENDER, 0));
        }
    }
}
