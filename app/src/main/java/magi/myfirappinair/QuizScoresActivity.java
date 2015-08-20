package magi.myfirappinair;

import android.content.res.XmlResourceParser;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class QuizScoresActivity extends QuizActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_scores);

        TabHost host = (TabHost) findViewById(R.id.TabHost01);
        host.setup();
        addALLFriendTab(host);
        getScoresByXmlResParse();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_scores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will∂
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public TabHost addALLFriendTab (TabHost host){
        TabHost.TabSpec allScoresTab = host.newTabSpec("allTab");
        allScoresTab.setIndicator(getResources().getString(R.string.all_scores),
                getResources().getDrawable(android.R.drawable.star_on));
        allScoresTab.setContent(R.id.ScrollViewAllScores);
        host.addTab(allScoresTab);

        TabHost.TabSpec friendsTab = host.newTabSpec("friendsTab");
        friendsTab.setIndicator(getResources().getString(R.string.friends_scores),
                getResources().getDrawable(android.R.drawable.star_on));
        friendsTab.setContent(R.id.ScrollViewFriendScores);
        host.addTab(friendsTab);
        host.setCurrentTabByTag("allTab");
        return host;
    }

    public void insertScoreRow(TableLayout scoreTable, String userName,
                               String value, String ranking){
        TableRow msg_row = new TableRow(this);

        TextView userName_tView = new TextView(this);
        TextView value_tView = new TextView(this);
        TextView ranking_tView = new TextView(this);
        userName_tView.setText(userName);

        value_tView.setText(value);
        ranking_tView.setText(ranking);
        userName_tView.setText(userName);

        msg_row.setPadding(40, 10 ,40, 10);

        //TODO show the message of scores
        msg_row.addView(userName_tView);
        scoreTable.addView(msg_row);
    }

    public void getScoresByXmlResParse(){
        XmlResourceParser mockAllScores = getResources().getXml(R.xml.allscores);
        XmlResourceParser mockFriendScores = getResources().getXml(R.xml.friendscores);

        int evenType = -1;
        boolean bFoundScores = false;
        //Find Score records from xml
        while (evenType != XmlResourceParser.END_DOCUMENT){
            if (evenType == XmlResourceParser.START_TAG){
                //get the name of the tag (eg scores of score)
                String strName = mockAllScores.getName();
                if (strName.equals("score")){
                    bFoundScores = true;
                    String scoreValue = mockAllScores.getAttributeValue(null, "score");
                    String scoreRank = mockAllScores.getAttributeValue(null, "rank");
                    String scoreUserName = mockAllScores.getAttributeValue(null, "username");
                    TableLayout tabLayout_allScores = (TableLayout) findViewById(R.id.TableLayout_AllScores);
                    insertScoreRow(tabLayout_allScores, scoreUserName,
                            scoreValue, scoreRank);
                }
            }
            try {
                evenType = mockAllScores.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
