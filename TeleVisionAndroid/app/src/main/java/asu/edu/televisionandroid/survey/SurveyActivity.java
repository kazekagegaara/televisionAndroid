package asu.edu.televisionandroid.survey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import asu.edu.televisionandroid.LogoutHandler;
import asu.edu.televisionandroid.LoginActivity;

import asu.edu.televisionandroid.R;


public class SurveyActivity extends Activity {
    private SurveyQuestionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        ArrayList<Question> questions = new ArrayList<Question>();
        BufferedReader input = null;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.surveyquestions);
            input = new BufferedReader(new InputStreamReader(
                    inputStream));
            String line;
            StringBuffer content = new StringBuffer();
            char[] buffer = new char[1024];
            int num;
            while ((num = input.read(buffer)) > 0) {
                content.append(buffer, 0, num);
            }
            JSONObject jsonObject = new JSONObject(content.toString());
            JSONArray array = jsonObject.getJSONArray("questions");
            for(int i = 0; i<array.length(); i++){
                questions.add(new Question(array.getJSONObject(i)));
            }

        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new SurveyQuestionsAdapter(this, R.layout.survey_question, questions.toArray(new Question[questions.size()]) );
        ListView listView = (ListView) findViewById(R.id.questionsList);
        listView.setAdapter(adapter);
        listView.deferNotifyDataSetChanged();

        Button clickButton = (Button) findViewById(R.id.buttonSurvey);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String allQuestionsAnswered = adapter.areAllQuestionsAnswered();
                if (allQuestionsAnswered.equals("true")) {
                    Intent myIntent = new Intent(SurveyActivity.this, SurveyReviewActivity.class);
                    Question[] ques = adapter.getQuestionsWithAnswers();
//                    getApplication().
                    myIntent.putParcelableArrayListExtra("ques", new ArrayList<Question>((Arrays.asList(ques))) );
                    SurveyActivity.this.startActivity(myIntent);
                } else {
                    Log.d(this.getClass().getSimpleName(), allQuestionsAnswered);
                }
            }
        });

        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LogoutHandler lh = new LogoutHandler();
                lh.deleteStoredFiles();
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(SurveyActivity.this, LoginActivity.class);
                SurveyActivity.this.startActivity(myIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey, menu);
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
