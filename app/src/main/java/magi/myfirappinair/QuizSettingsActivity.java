package magi.myfirappinair;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.prefs.PreferenceChangeEvent;


public class QuizSettingsActivity extends QuizActivity {

    private static final int DATE_DIALOG_ID = 0;
    private static final int PASSWORD_DIALOG_ID = 1;

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
        showDialog(DATE_DIALOG_ID);
        Toast.makeText(QuizSettingsActivity.this, "TODO: launch DataPickDialog",
                Toast.LENGTH_LONG).show();
    }

    public void onSetPasswordButtonClick(View view){
        showDialog(PASSWORD_DIALOG_ID);
        Toast.makeText(QuizSettingsActivity.this, "TODO: Launch Password Dialog",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
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
        SharedPreferences mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
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
        SharedPreferences mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        final EditText nicknameText = (EditText) findViewById(R.id.EditText_Nickname);
        final EditText emailText = (EditText) findViewById(R.id.EditText_Email);
        final TextView passwordText = (TextView) findViewById(R.id.TextView_Password_Info);
        final TextView birthDateText = (TextView) findViewById(R.id.TextViewBirthDate);
        final Spinner genderSpinner = (Spinner) findViewById(R.id.Spinner_Gender);

        if (mGameSettings.contains(GAME_PREFERENCES_NICKNAME)) {
            nicknameText.setText(mGameSettings.getString(GAME_PREFERENCES_NICKNAME, ""));
        }
        if (mGameSettings.contains(GAME_PREFERENCES_EMAIL)) {
            emailText.setText(mGameSettings.getString(GAME_PREFERENCES_EMAIL, ""));
        }
        if (mGameSettings.contains(GAME_PREFERENCES_PASSWORD)) {
            passwordText.setText(mGameSettings.getString(GAME_PREFERENCES_PASSWORD, ""));
        }
        if (mGameSettings.contains(GAME_PREFERENCES_DOB)) {
            long longDOB = mGameSettings.getLong(GAME_PREFERENCES_DOB, 0);
            birthDateText.setText(android.text.format.DateFormat.format("MMMM dd, yyyy", longDOB));
        }
        if (mGameSettings.contains(GAME_PREFERENCES_GENDER)) {
            genderSpinner.setSelection(mGameSettings.getInt(GAME_PREFERENCES_GENDER, 0));
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        SharedPreferences mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        switch (id) {

            case DATE_DIALOG_ID: {
                final SharedPreferences.Editor editor = mGameSettings.edit();
                final TextView dob = (TextView) findViewById(R.id.TextViewBirthDate);
                Calendar now = Calendar.getInstance();
                DatePickerDialog dateDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                android.text.format.Time dateOfBirth = new android.text.format.Time();
                                dateOfBirth.set(dayOfMonth, monthOfYear, year);
                                long dtDob = dateOfBirth.toMillis(true);
                                dob.setText(android.text.format.DateFormat.format("MMMM dd, yyyy", dtDob));
                                editor.putLong(GAME_PREFERENCES_DOB, dtDob);
                                editor.apply();
                            }
                        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                return dateDialog;
            }

            case PASSWORD_DIALOG_ID: {
                //Build and return Dialog
                LayoutInflater inflater = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.password_dialog,
                        (ViewGroup) findViewById(R.id.root));
                final EditText p1 = (EditText) layout.findViewById(R.id.EditText_Pwd1);
                final EditText p2 = (EditText) layout.findViewById(R.id.EditText_Pwd2);
                final TextView error = (TextView) layout.findViewById(R.id.TextView_PwdProblem);
                p2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String strPass1 = p1.getText().toString();
                        String strPass2 = p2.getText().toString();
                        if (strPass1.equals(strPass2)) {
                            error.setText(R.string.settings_pwd_equal);
                            error.setTextColor(getResources().getColor(R.color.title_color));
                        } else {
                            error.setText(R.string.settings_pwd_not_equal);
                            error.setTextColor(getResources().getColor(R.color.error_color));
                        }
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(layout);
                builder.setTitle(R.string.set_password);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView passwordInfo = (TextView) findViewById(
                                R.id.TextView_Password_Info);
                        String strPassword1 = p1.getText().toString();
                        String strPassword2 = p2.getText().toString();
                        if (strPassword1.equals(strPassword2)) {
                            SharedPreferences mGameSettings = getSharedPreferences(
                                    GAME_PREFERENCES, MODE_PRIVATE);
                            SharedPreferences.Editor editor = mGameSettings.edit();
                            editor.putString(GAME_PREFERENCES_PASSWORD, strPassword1);
                            editor.commit();
                            passwordInfo.setText(strPassword1);
                        } else {
                            Log.d(DEBUG_TAG, "Passwords do not match. " +
                                    "Not saving. Keeping old password (if set).");
                            Toast.makeText(QuizSettingsActivity.this, "Not saving \n Passwords do not match",
                                    Toast.LENGTH_LONG).show();
                        }
                        QuizSettingsActivity.this.dismissDialog(PASSWORD_DIALOG_ID);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                QuizSettingsActivity.this.removeDialog(PASSWORD_DIALOG_ID);
                            }
                        });
                AlertDialog passwordDialog = builder.create();
                return passwordDialog;

            }
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        SharedPreferences mGameSettings = getSharedPreferences(GAME_PREFERENCES,
                Context.MODE_PRIVATE);
        switch (id) {
            case DATE_DIALOG_ID: {
                //Handle any DatePickerDialog initialization here
                DatePickerDialog dateDialog = (DatePickerDialog) dialog;
                int iDay, iMonth, iYear;
                //Check for date of birth preference
                if (mGameSettings.contains(GAME_PREFERENCES_DOB)) {
                    // Retrieve Birth date setting from preferences
                    long msBirthDate = mGameSettings.getLong(GAME_PREFERENCES_DOB, 0);
                    android.text.format.Time dateOfBirth = new android.text.format.Time();
                    dateOfBirth.set(msBirthDate);
                    iDay = dateOfBirth.monthDay;
                    iMonth = dateOfBirth.month;
                    iYear = dateOfBirth.year;
                } else {
                    Calendar cal = Calendar.getInstance();
                    //Today's date fields
                    iDay = cal.get(Calendar.DAY_OF_MONTH);
                    iMonth = cal.get(Calendar.MONTH);
                    iYear = cal.get(Calendar.YEAR);
                }
                // Set the date in the DatePicker to te date of birth OR the current date
                dateDialog.updateDate(iYear, iMonth, iDay);
                break;
            }
           /* case PASSWORD_DIALOG_ID: {
                LayoutInflater inflater = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.password_dialog,
                        (ViewGroup) findViewById(R.id.root));
                final EditText p1 = (EditText) layout.findViewById(R.id.EditText_Pwd1);
                if (mGameSettings.contains(GAME_PREFERENCES_PASSWORD)) {
                    p1.setText(mGameSettings.getString(GAME_PREFERENCES_PASSWORD, ""));
                }
            }*/
        }
        super.onPrepareDialog(id, dialog);
    }

}
