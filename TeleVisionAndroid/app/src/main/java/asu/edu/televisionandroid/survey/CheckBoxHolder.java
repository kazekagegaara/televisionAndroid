package asu.edu.televisionandroid.survey;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import asu.edu.televisionandroid.R;

/**
 * Created by student on 4/8/15.
 */
public class CheckBoxHolder implements ListItem {
    private TextView txtTitle;
    private LinearLayout group;
    private Question question;
    private ArrayList<CheckBox> boxes;
    private boolean[] checked;
    public CheckBoxHolder(View row, Question ques, Context context){
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
            if(checked[box.getId()])
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
//            if(selectedIndex > -1)putParcelableArrayListExtra
//                this.group.check(selectedIndex);
    }
}
