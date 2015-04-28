package asu.edu.televisionandroid;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;


public class ChoiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);


        Button clickButton = (Button) findViewById(R.id.captureSingleImage);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ChoiceActivity.this, ImagePickerActivity.class);
                ChoiceActivity.this.startActivity(myIntent);
            }
        });

        Button clickButton2 = (Button) findViewById(R.id.captureVideo);
        clickButton2.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ChoiceActivity.this, VideoCaptureActivity.class);
                ChoiceActivity.this.startActivity(myIntent);
            }
        });

        Button clickButton3 = (Button) findViewById(R.id.captureTwoImage);
        clickButton3.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ChoiceActivity.this, ImagePickerActivity2.class);
                ChoiceActivity.this.startActivity(myIntent);
            }
        });
    }

}
