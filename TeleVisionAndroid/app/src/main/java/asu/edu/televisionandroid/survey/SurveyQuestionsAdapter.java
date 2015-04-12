package asu.edu.televisionandroid.survey;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import asu.edu.televisionandroid.R;

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
 * @version March 29, 2015
 */
public class SurveyQuestionsAdapter extends ArrayAdapter<Question> {
    private ArrayList<Question> questions;
    private Context context;
    private int layoutResourceId;
    private ArrayList<View> rows;
    public SurveyQuestionsAdapter(Context context, int resource, Question[] ques) {
        super(context, resource, ques);
        Log.d(this.getClass().getSimpleName(), "intializing SurveyQuestionsAdapter");
        this.context= context;
        this.layoutResourceId = resource;
        questions = new ArrayList<>();
        questions.addAll(Arrays.asList(ques));
        rows = new ArrayList<>();
        for(Question q : questions){
            ListItem holder = null;

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View row = inflater.inflate(layoutResourceId, null, false);
            if(q.isMultiSelect())
                holder = new CheckBoxHolder(row, q, context);
            else
                holder = new RadioHolder (row, q, context);
            rows.add(row);
            row.setTag(holder);

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(this.getClass().getSimpleName(), "in getView");
        View row = convertView;
        ListItem holder = null;
        Question ques = questions.get(position);

        if (row == null) {
            row = rows.get(position);
        }
        holder = (ListItem) row.getTag();

        holder.getTextView().setText(ques.getQuestion());
//        holder.refreshViews();
        return row;
    }

    public HashMap<String, ArrayList<String>> getQuestionAnswerMap(){
        Log.d(this.getClass().getSimpleName(), "in getQuestionAnswerMap");
        HashMap<String, ArrayList<String>> questionAnswerMap = new HashMap<>();
        for(View view : rows){
            ListItem item = (ListItem) view.getTag();
            Question ques = item.getQuestion();
            int[] choices = item.getChoices();
            ArrayList<String> answers = new ArrayList<>();
            for( int i = 0; i< choices.length; i++)
                answers.add(ques.getOptions().get(choices[i]));
            questionAnswerMap.put(ques.getQuestion(), answers);
        }
        return questionAnswerMap;
    }

    public String areAllQuestionsAnswered(){
        Log.d(this.getClass().getSimpleName(), "in areAllQuestionsAnswered");
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<rows.size(); i++){
            if(!((ListItem)(rows.get(i).getTag())).isValueChosen())
                sb.append("Question "+(i+1)+", " );
        }
        if(sb.toString().length() == 0){
            sb.append("true");
        }else{
            sb.append("Not answered");
        }
        return sb.toString();
    }

    @Override
    public int getViewTypeCount() {
        //Count=Size of ArrayList.
        return questions.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public Question[] getQuestionsWithAnswers() {
        for(int i=0; i<rows.size(); i++){
//            if(!((ListItem)(rows.get(i).getTag())).isValueChosen())
                questions.get(i).setAnswer(((ListItem)(rows.get(i).getTag())).getChoices());
        }
        return questions.toArray(new Question[questions.size()]);
    }
}
