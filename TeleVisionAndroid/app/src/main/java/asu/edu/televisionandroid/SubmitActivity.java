package asu.edu.televisionandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.util.ArrayList;

public class SubmitActivity extends Activity {

    private static final String IMAGE_DIRECTORY_NAME = "MayoClinicStorage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
//        System.out.println("here");
        getStoredFiles();
        Button clickButton = (Button) findViewById(R.id.buttonSubmit);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(SubmitActivity.this, patientIDActivity.class);
                SubmitActivity.this.startActivity(myIntent);
            }
        });

        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LogoutHandler lh = new LogoutHandler();
                lh.deleteStoredFiles();
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(SubmitActivity.this, LoginActivity.class);
                SubmitActivity.this.startActivity(myIntent);
            }
        });
    }

    public ArrayList<String> getStoredFiles() {
//        System.out.println("Here");
        ArrayList<String> filePaths = new ArrayList<String>();

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        // check for directory
        if (mediaStorageDir.isDirectory()) {
            // getting list of file paths
            File[] listFiles = mediaStorageDir.listFiles();
            // Check for count
            if (listFiles.length > 0) {
                for (int i = 0; i < listFiles.length; i++) {
                    String filePath = listFiles[i].getAbsolutePath();
                    filePaths.add(filePath);
                }
            }
        }
        System.out.println(filePaths.toString());
        return filePaths;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
