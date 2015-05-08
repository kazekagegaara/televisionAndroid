package asu.edu.televisionandroid.survey;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import asu.edu.televisionandroid.R;

/**
 * Created by Akshay Ashwathanarayana on 4/11/15.
 */
public class SurveyReviewAdapter  extends ArrayAdapter<Question> {
    private ArrayList<Question> questions;
    private Context context;
    private int layoutResourceId;

    public SurveyReviewAdapter(Context context, int resource, Question[] ques) {
        super(context, resource, ques);
        Log.d(this.getClass().getSimpleName(), "intializing SurveyQuestionsAdapter");
        this.context= context;
        this.layoutResourceId = resource;
        questions = new ArrayList<>();
        questions.addAll(Arrays.asList(ques));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(this.getClass().getSimpleName(), "in getView");
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.survey_review_listitem, parent, false);
        Question ques = questions.get(position);
        TextView quesTextView = (TextView) rowView.findViewById(R.id.question);
        TextView ansTextView = (TextView) rowView.findViewById(R.id.answer);
        quesTextView.setText(ques.getQuestion());
        ansTextView.setText(ques.getAnswerAsString());
        return rowView;
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
        return questions.toArray(new Question[questions.size()]);
    }
}
