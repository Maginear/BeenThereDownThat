package magi.myfirappinair;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class QuizSplashActivity extends QuizActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_splash);
/*        SharedPreferences
                setting = getSharedPreferences(LASTLAUNCH, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = setting.edit();
        prefEditor.putString("Now", new Date().toString());
        prefEditor.commit();

        if(setting.contains("Now") == true){
            String now =  setting.getString("Now", "Default");
            TextView tv = new TextView(this);
            tv.setText(now);
            setContentView(tv);
            prefEditor.putString("Now", new Date().toString());
            prefEditor.commit();
        }*/

        TextView logo01 = (TextView)findViewById(R.id.TextViewTopTitle);
        Animation fade01= AnimationUtils.loadAnimation(this, R.anim.fade_in01);
        logo01.startAnimation(fade01);

        Animation spinin = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        LayoutAnimationController controller = new LayoutAnimationController(spinin);
        TableLayout table =(TableLayout) findViewById(R.id.TableLayout01);
        for (int i = 0; i < table.getChildCount(); i++){
            TableRow row = (TableRow) table.getChildAt(i);
            row.setLayoutAnimation(controller);
        }

        TextView logo02 = (TextView)findViewById(R.id.TextViewBottomTitle);
        Animation fade02 = AnimationUtils.loadAnimation(this, R.anim.fade_in02);
        fade02.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(QuizSplashActivity.this,QuizMenuActvity.class));
                QuizSplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logo02.startAnimation(fade02);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_splash, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
        //Stop the animation
        TextView logo1 = (TextView)findViewById(R.id.TextViewTopTitle);
        logo1.clearAnimation();

        TextView logo2 = (TextView)findViewById(R.id.TextViewBottomTitle);
        logo2.clearAnimation();

        TextView logo3 = (TextView)findViewById(R.id.TextViewBottomVersion);
        logo3.clearAnimation();

        TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);
        for (int i = 0; i < table.getChildCount(); i++){
            TableRow row = (TableRow) table.getChildAt(i);
            row.clearAnimation();
        }
        //...stop other animations
    }
}
















