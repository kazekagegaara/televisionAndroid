package asu.edu.televisionandroid.survey;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import asu.edu.televisionandroid.ChoiceActivity;
import asu.edu.televisionandroid.ImagePickerActivity;
import asu.edu.televisionandroid.R;
import asu.edu.televisionandroid.LoginActivity;
import asu.edu.televisionandroid.LogoutHandler;

/**
 * Created by Akshay Ashwathanarayana on 4/11/15.
 */
public class SurveyReviewActivity extends Activity {
    private SurveyReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("in review"  );
        setContentView(R.layout.activity_survey_review);
        ArrayList<Question> questions = getIntent().getParcelableArrayListExtra("ques");


        adapter = new SurveyReviewAdapter(this, R.layout.survey_question, questions.toArray(new Question[questions.size()]) );
        ListView listView = (ListView) findViewById(R.id.questionsList);
        listView.setAdapter(adapter);
        listView.deferNotifyDataSetChanged();

        Button clickButton = (Button) findViewById(R.id.buttonSurvey);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SurveyReviewActivity.this, ChoiceActivity.class);
                SurveyReviewActivity.this.startActivity(myIntent);
            }
        });

        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LogoutHandler lh = new LogoutHandler();
                lh.deleteStoredFiles();
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(SurveyReviewActivity.this, LoginActivity.class);
                SurveyReviewActivity.this.startActivity(myIntent);
                LayoutInflater inflater = getLayoutInflater();
                View pdfView = inflater.inflate(R.layout.pro_pdf_page, null, false);
                ListView listView2 = (ListView) pdfView.findViewById(R.id.listView);
                listView2.setAdapter(adapter);
                // create a new document
                PdfDocument document = new PdfDocument();

                // crate a page description
                PdfDocument.PageInfo pageInfo;
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width=dm.widthPixels;
                int height=dm.heightPixels;
                pageInfo = new PdfDocument.PageInfo.Builder(width, height, 1).create();

                // start a page
                PdfDocument.Page page = document.startPage(pageInfo);

                // draw something on the page
                RelativeLayout content = (RelativeLayout)findViewById(R.id.wholeView);
                content.draw(page.getCanvas());

                // finish the page
                document.finishPage(page);
               /* . . .
                // add more pages
                . . .
               */ // write the document content
                String externalPath = Environment.getExternalStorageDirectory().toString();
                File outputFolder = new File(externalPath+"/TeleVisionPRO");
                File file = new File(outputFolder.getAbsolutePath(), "proReport.pdf");
                if(!outputFolder.exists()){
                    outputFolder.mkdir();
                }
                try {
                    file.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(file);
                    document.writeTo(outputStream);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }


                // close the document
                document.close();

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
