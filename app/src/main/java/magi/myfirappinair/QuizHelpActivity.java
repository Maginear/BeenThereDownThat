package magi.myfirappinair;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;


public class QuizHelpActivity extends QuizActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_help);
        InputStream inFile = getResources().openRawResource(R.raw.quizhelp);
        String strHelp = inputStreamToString(inFile);
        TextView textHelp = (TextView) findViewById(R.id.TextVIew_HelpText);
        textHelp.setText(strHelp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_help, menu);
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

    public String inputStreamToString(InputStream is){
        StringBuffer sBuffer = new StringBuffer();
        DataInputStream dataIO = new DataInputStream(is);
        String strline = null;
        try {
            while ((strline = dataIO.readLine()) != null){
                sBuffer.append(strline + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sBuffer.toString();
    }
}
