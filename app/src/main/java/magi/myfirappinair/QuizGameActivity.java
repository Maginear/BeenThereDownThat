package magi.myfirappinair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;


public class QuizGameActivity extends QuizActivity {

    private TextSwitcher mQuestionText;
    private ImageSwitcher mQuestionImage;
    private Hashtable<Integer, Question> mQuestions;
    private SharedPreferences mGameSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        mQuestions = new Hashtable<>(QUESTION_BATCH_SIZE);

        int startQuestionNumber = mGameSettings.getInt(GAME_PREFERENCES_CURRENT_QUESTION, 0);

        mQuestionText = (TextSwitcher) findViewById(R.id.TextSwitcher_QuestionText);
        mQuestionText.setFactory(new MyTextSwitcherFactory());

        mQuestionImage = (ImageSwitcher) findViewById(R.id.ImageSwitcher_QuestionImage);
        mQuestionImage.setFactory(new MyImageSwitcherFactory());

        mQuestionText.setCurrentText("First Question Text");
        //mQuestionImage.setImageDrawable;

        // Initialize the Shared preferences
        if (startQuestionNumber == 0) {
            SharedPreferences.Editor editor = mGameSettings.edit();
            editor.putInt(GAME_PREFERENCES_CURRENT_QUESTION, 1);
            editor.apply();
            startQuestionNumber = 1;
        }


        // Load the question
        try {
            loadQuestionBatch(startQuestionNumber);
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Loading initial question batch failed", e);
        }


        // If the question was loaded properly, display it
        if (mQuestions.containsKey(startQuestionNumber)) {
            // Set the text of the textSwitcher
            mQuestionText.setCurrentText(getQuestionText(startQuestionNumber));

            // Set the image of the imageSwitcher
            Drawable image = getQuestionImageDrawable(startQuestionNumber);
            mQuestionImage.setImageDrawable(image);
        } else {
            // Tell the user we don't have any new questions at this time
            handleNoQuestions();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_quiz_game, menu);

        menu.findItem(R.id.help_menu_item).setIntent(new Intent(
                this, QuizHelpActivity.class));
        menu.findItem(R.id.settings_menu_item).setIntent(new Intent(
                this, QuizSettingsActivity.class));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        startActivity(item.getIntent());

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyImageSwitcherFactory implements ViewSwitcher.ViewFactory {
        @Override
        public View makeView() {
            return LayoutInflater.from(
                    getApplicationContext()).inflate(R.layout.image_switcher_view,
                    mQuestionImage, false);
        }
    }

    private class MyTextSwitcherFactory implements ViewSwitcher.ViewFactory {
        @Override
        public View makeView() {
            return LayoutInflater.from(
                    getApplicationContext()).inflate(R.layout.text_switcher_view,
                    mQuestionText, false);

        }
    }

    private void handleNoQuestions() {
        TextSwitcher questionTextSwitcher = (TextSwitcher) findViewById(R.id.TextSwitcher_QuestionText);
        questionTextSwitcher.setText(getResources().getText(R.string.no_questions));
        ImageSwitcher questionImageSwitcher = (ImageSwitcher) findViewById(R.id.ImageSwitcher_QuestionImage);
        questionImageSwitcher.setImageResource(R.drawable.noquestion);

        // Disable yes button
        Button yesButton = (Button) findViewById(R.id.Button_Yes);
        yesButton.setEnabled(false);

        // Disable no button
        Button noButton = (Button) findViewById(R.id.Button_No);
        noButton.setEnabled(false);
    }

    public void onYesButton(View view) {
        handleAnswerAndShowNextQuestion(true);
    }


    public void onNoButton (View view) {
        handleAnswerAndShowNextQuestion(false);
    }

    private Drawable getQuestionImageDrawable(int questionNumber) {
        Drawable image;
        URL imageUrl; 
        try {
            imageUrl = new URL(getQuestionImageUrl(questionNumber));
            InputStream stream = imageUrl.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            image = new BitmapDrawable(getResources(), bitmap);
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Decoding Bitmap stream failed");
            image = getResources().getDrawable(R.drawable.noquestion);
        }
        return image;
    }

    private class Question {
        @SuppressWarnings("unused")
        int mNumber;
        String mText;
        String mImageUrl;

        /**
         * Constructs a new question object
         *
         * @param questionNum      The number of this question
         * @param questionText     The text for this question
         * @param questionImageUrl A valid image Url to display with this question
         */
        public Question(int questionNum, String questionText, String questionImageUrl) {
            mNumber = questionNum;
            mText = questionText;
            mImageUrl = questionImageUrl;
        }
    }

    private void handleAnswerAndShowNextQuestion(boolean bAnswer) {
        // Load game settings like score and current question
        int curScore = mGameSettings.getInt(GAME_PREFERENCES_SCORE, 0);
        int nextQuestionNumber = mGameSettings.getInt(GAME_PREFERENCES_CURRENT_QUESTION, 1) + 1;

        // Update score if answer is "yes"
        SharedPreferences.Editor editor = mGameSettings.edit();
        editor.putInt(GAME_PREFERENCES_CURRENT_QUESTION, nextQuestionNumber);
        if (bAnswer) {
            editor.putInt(GAME_PREFERENCES_SCORE, curScore + 1);
        }
        editor.apply();

        // Load the next question, handling if there are no more questions
        if (mQuestions.containsKey(nextQuestionNumber)) {
            // Load next batch
            try {
                loadQuestionBatch(nextQuestionNumber);
            } catch (Exception e) {
                Log.e(DEBUG_TAG, "Loading updated question batch failed", e);
            }
        }

        if (mQuestions.containsKey(nextQuestionNumber)) {
            //Update question text
            TextSwitcher questionTextSwitcher = (TextSwitcher) findViewById(R.id.TextSwitcher_QuestionText);
            questionTextSwitcher.setText(getQuestionText(nextQuestionNumber));

            //Update question image
            ImageSwitcher questionImageSwitcher = (ImageSwitcher) findViewById(R.id.ImageSwitcher_QuestionImage);
            Drawable image = getQuestionImageDrawable(nextQuestionNumber);
            questionImageSwitcher.setImageDrawable(image);
        } else {
            handleNoQuestions();
        }
    }

    /**
     * Returns a {@code String} representing the URL to an image for a particular question
     *
     * @param questionNumber The question to get the URL for
     * @return A {@code String} for the URL or null if none found
     */
    private String getQuestionImageUrl(Integer questionNumber) {
        String url = null;
        Question curQuestion = mQuestions.get(questionNumber);
        if (curQuestion != null) {
            url = curQuestion.mImageUrl;
        }
        return url;
    }

    /**
     * Returns a {@code String} representing the text for a particular question number
     *
     * @param questionNumber The question number to get the text for
     * @return The text of the question, or null if {@code questionNumber} not found
     */
    private String getQuestionText(Integer questionNumber) {
        String text = null;
        Question curQuestion;
        curQuestion = mQuestions.get(questionNumber);
        if (curQuestion != null) {
            text = curQuestion.mText;
        }
        return text;
    }

    /**
     * Loads the XML into the {@see mQuestions} class member variable
     *
     * @param startQuestionNumber TODO: currently unused
     * @throws XmlPullParserException Thrown if XML parsing errors
     * @throws IOException            Thrown if errors loading XML
     */
    private void loadQuestionBatch(int startQuestionNumber) throws XmlPullParserException, IOException {
        // Remove old batch
        mQuestions.clear();

        // TODO: Contact the server and retrieve a batch of question data, beginning at startQuestionNumber
        XmlResourceParser questionBatch;

        // BEGIN MOCK QUESTIONS
        if (startQuestionNumber < 16) {
            questionBatch = getResources().getXml(R.xml.samplequestions01);
        } else {
            questionBatch = getResources().getXml(R.xml.samplequestions02);
        }
        // END MOCK QUESTIONS

        // Parse the XML
        int eventType = -1;

        // Find Score records from XML
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {

                // Get the name of the tag (eg questions or question)
                String strName = questionBatch.getName();

                if (strName.equals(XML_TAG_QUESTION)) {

                    String questionNumber = questionBatch.getAttributeValue(null, XML_TAG_QUESTION_ATTRIBUTE_NUMBER);
                    Integer questionNum = Integer.valueOf(questionNumber);
                    String questionText = questionBatch.getAttributeValue(null, XML_TAG_QUESTION_ATTRIBUTE_TEXT);
                    String questionImageUrl = questionBatch.getAttributeValue(null, XML_TAG_QUESTION_ATTRIBUTE_IMAGEURL);

                    // Save data to our hashtable
                    mQuestions.put(questionNum, new Question(questionNum, questionText, questionImageUrl));
                }
            }
            eventType = questionBatch.next();
        }
    }
}
