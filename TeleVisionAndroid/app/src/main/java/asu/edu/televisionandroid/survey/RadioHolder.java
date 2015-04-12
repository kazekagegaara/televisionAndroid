package asu.edu.televisionandroid.survey;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import asu.edu.televisionandroid.R;

/**
 * Created by student on 4/8/15.
 */
public class RadioHolder implements ListItem{
    private TextView txtTitle;
    private RadioGroup group;
    private Question question;
    private int selectedIndex = -1;
    private ArrayList<RadioButton> buttons;
    public RadioHolder(View row, Question ques, Context context){
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
