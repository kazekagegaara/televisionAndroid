package asu.edu.televisionandroid.survey;

import android.widget.TextView;

/**
 * Created by student on 4/8/15.
 */
public interface ListItem {
    public boolean isValueChosen();
    public int[] getChoices();
    public TextView getTextView();
    public Question getQuestion();
    public void refreshViews();
}
