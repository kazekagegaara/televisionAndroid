package asu.edu.televisionandroid.survey;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
public class Question implements Parcelable{
    private String question;
    private ArrayList<String> options;
    private boolean multiSelect = false;
    private int[] answer;

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public Question(boolean multiSelect, ArrayList<String> options, String question) {
        this.multiSelect = multiSelect;
        this.options = options;
        this.question = question;
    }

    public Question(Parcel source){
        /*dest.writeString(question);
        dest.writeList(options);
        dest.writeString(new Boolean(multiSelect).toString());
        dest.writeIntArray(answer); */

        question = source.readString();
        options = new ArrayList<String>();
        source.readStringList(options);
        multiSelect = Boolean.parseBoolean(source.readString());
        answer= new int[source.readInt()];
        source.readIntArray(answer);

    }

    public Question(JSONObject object) {
        try {
            this.multiSelect = object.getBoolean("multiselect");
            this.question = object.getString("question");
            JSONArray optionArray = object.getJSONArray("options");
            this.options = new ArrayList<String>();
            for(int i = 0; i<optionArray.length(); i++){
                options.add(optionArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringList(options);
        dest.writeString(new Boolean(multiSelect).toString());
        dest.writeInt(answer.length);
        dest.writeIntArray(answer);
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getAnswerAsString() {
        if(answer != null){
            StringBuilder sb = new StringBuilder();
            for(int i : answer) {
                if(sb.length() != 0)
                    sb.append(", ");
                sb.append(options.get(i));
            }
            return sb.toString();
        }
        return "";
    }
}
