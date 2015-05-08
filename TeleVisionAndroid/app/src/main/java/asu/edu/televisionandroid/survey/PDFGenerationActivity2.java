package asu.edu.televisionandroid.survey;

import android.app.Activity;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;

import asu.edu.televisionandroid.R;
import asu.edu.televisionandroid.SubmitActivity;

/**
 * Copyright (C) 2006 Akshay Ashwathanarayana
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Akshay Ashwathanarayana mailto:Akshay.Ashwathanarayana@asu.edu
 * @version April 28, 2015
 */
public class PDFGenerationActivity2 extends Activity {
    private SurveyReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_pdf_answers_page);
        ArrayList<Question> questions = getIntent().getParcelableArrayListExtra("ques");
        SubmitActivity activity = new SubmitActivity();

        adapter = new SurveyReviewAdapter(this, R.layout.survey_question, questions.toArray(new Question[questions.size()]) );
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.deferNotifyDataSetChanged();

        final RelativeLayout content = (RelativeLayout)findViewById(R.id.wholeView);
        ViewTreeObserver vto = content.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // create a new document
                PdfDocument document = SubmitActivity.document;
                if (document.getPages().size() <  2) {
                    // crate a page description
                    PdfDocument.PageInfo pageInfo;
                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                    int width = dm.widthPixels;
                    int height = dm.heightPixels;
                    pageInfo = new PdfDocument.PageInfo.Builder(width, height, 1).create();

                    // start a page
                    PdfDocument.Page page = document.startPage(pageInfo);

                    // draw something on the page
                    RelativeLayout content = (RelativeLayout) findViewById(R.id.wholeView);
//            LinearLayout content2 = (LinearLayout) pdfView.findViewById(R.id.wholeView);
                    content.draw(page.getCanvas());

                    // finish the page
                    document.finishPage(page);
           /* . . .
            // add more pages
            . . .
           */ // write the document content


                    // close the document
//                document.close();
//                ArrayList<Question> questions = getIntent().getParcelableArrayListExtra("ques");
                    Intent myIntent = new Intent(PDFGenerationActivity2.this, SubmitActivity.class);
//                myIntent.putParcelableArrayListExtra("ques", questions );
                    PDFGenerationActivity2.this.startActivity(myIntent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
