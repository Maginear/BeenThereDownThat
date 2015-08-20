package magi.myfirappinair;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class QuizMenuActvity extends QuizActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);
        ListView menuList = (ListView) findViewById(R.id.ListView_Menu);
        String[] items = {getResources().getString(R.string.menu_item_play),
            getResources().getString(R.string.menu_item_scores),
            getResources().getString(R.string.menu_item_settings),
            getResources().getString(R.string.menu_item_help)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.menu_item, items);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String strText = textView.getText().toString();
                if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_play))) {
                    //Launch the Game Activity
                    startActivity(new Intent(QuizMenuActvity.this, QuizGameActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_scores))) {
                    //Launch the Scores Activity
                    startActivity(new Intent(QuizMenuActvity.this, QuizScoresActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_settings))) {
                    //Launch the Setting Activity
                    startActivity(new Intent(QuizMenuActvity.this, QuizSettingsActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_help))) {
                    //Launch the Scores Activity
                    startActivity(new Intent(QuizMenuActvity.this, QuizHelpActivity.class));
                }
            }
        });
        menuList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_menu, menu);
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
