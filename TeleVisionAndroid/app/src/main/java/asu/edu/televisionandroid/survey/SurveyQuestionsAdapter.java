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
                holder = new CheckBoxHolder(row, q);
            else
                holder = new RadioHolder (row, q);
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

    private static interface ListItem{
        public boolean isValueChosen();
        public int[] getChoices();
        public TextView getTextView();
        public Question getQuestion();
        public void refreshViews();
    }
    private class RadioHolder implements ListItem{
        private TextView txtTitle;
        private RadioGroup group;
        private Question question;
        private int selectedIndex = -1;
        private ArrayList<RadioButton> buttons;
        public RadioHolder(View row, Question ques){
            Log.d(this.getClass().getSimpleName(), "init RadioHolder");
            this.txtTitle = (TextView) row.findViewById(R.id.question);
            this.group = (RadioGroup) row.findViewById(R.id.radio_group1);
            this.question = ques;
            buttons = new ArrayList<>();
            for(int i=0; i<ques.getOptions().size(); i++){
                RadioButton button  = new RadioButton(context);
//                rb[i].setButtonDrawable(R.drawable.single_radio_chice);
                button.setId(i);
                button.setText(ques.getOptions().get(i));
//                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
//                        0, LinearLayout.LayoutParams.WRAP_CONTENT);
//                params.weight=1.0f;
//                params.setMargins(15, 0, 5, 10);
//                params.gravity = Gravity.CENTER_HORIZONTAL;
                this.group.addView(button); //the RadioButtons are added to the radioGroup instead of the layout
                buttons.add(button);
            }
            this.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    selectedIndex = checkedId;
                }
            });
        }
        @Override
        public boolean isValueChosen() {
            return group.getCheckedRadioButtonId() != -1;
        }

        @Override
        public int[] getChoices() {
            return new int[]{group.getCheckedRadioButtonId()};
        }

        @Override
        public TextView getTextView() {
            return this.txtTitle;
        }

        @Override
        public Question getQuestion() {
            return this.question;
        }

        @Override
        public void refreshViews() {
            this.txtTitle.setText(question.getQuestion());
            this.group.clearCheck();
            if(selectedIndex > -1)
                this.group.check(selectedIndex);
            for(int i= 0; i< buttons.size(); i++)
                buttons.get(i).setText(question.getOptions().get(i));
        }
    }

    private class CheckBoxHolder implements ListItem{
        private TextView txtTitle;
        private LinearLayout group;
        private Question question;
        private ArrayList<CheckBox> boxes;
        private boolean[] checked;
        public CheckBoxHolder(View row, Question ques){
            Log.d(this.getClass().getSimpleName(), "init CheckBoxHolder");
            this.txtTitle = (TextView) row.findViewById(R.id.question);
            this.group = (LinearLayout) row.findViewById(R.id.check_group1);
            boxes = new ArrayList<>();
            checked = new boolean[ques.getOptions().size()];
            this.question = ques;
            for(int i=0; i<ques.getOptions().size(); i++){
                CheckBox button  = new CheckBox(context);
                button.setId(i);
                button.setText(ques.getOptions().get(i));
                this.group.addView(button); //the CheckBoxes are added to the ListView
                boxes.add(button);
                button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checked[buttonView.getId()] = isChecked;
                    }
                });
            }
        }
        @Override
        public boolean isValueChosen() {
            return true;
        }

        @Override
        public int[] getChoices() {
            ArrayList<Integer> chosenIndices  = new ArrayList<>();
            for(CheckBox box : boxes){
                if(box.isSelected())
                    chosenIndices.add(box.getId());
            }
            int[] array = new int[chosenIndices.size()];
            for(int i=0; i<chosenIndices.size(); i++)
                array[i] = chosenIndices.get(i);

            return array;
        }

        @Override
        public TextView getTextView() {
            return this.txtTitle;
        }

        @Override
        public Question getQuestion() {
            return this.question;
        }

        @Override
        public void refreshViews() {
            this.txtTitle.setText(question.getQuestion());
//            this.group.clearCheck();
//            if(selectedIndex > -1)
//                this.group.check(selectedIndex);
        }
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
}
