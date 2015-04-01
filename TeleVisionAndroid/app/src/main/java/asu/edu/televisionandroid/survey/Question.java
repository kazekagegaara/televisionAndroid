package asu.edu.televisionandroid.survey;

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
public class Question {
    private String question;
    private ArrayList<String> options;
    private boolean multiSelect = false;

    public Question(boolean multiSelect, ArrayList<String> options, String question) {
        this.multiSelect = multiSelect;
        this.options = options;
        this.question = question;
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
}
