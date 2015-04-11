package asu.edu.televisionandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends Activity {

    EditText pass;
    EditText user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button clickButton = (Button) findViewById(R.id.loginButton);
    }


    public void onLoginClicked(View v) {
        pass = (EditText) findViewById(R.id.getpassword);
        user = (EditText) findViewById(R.id.getusername);

        int ifactive = 2;
        String utxt = pass.getText().toString();
        String ptxt = user.getText().toString();

        if(utxt.equals("demo") && ptxt.equals("demo"))
            ifactive = 1;
//        LoginAsync checkuser = new LoginAsync(utxt, ptxt);
//        checkuser.execute();
//
//        try {
//            ifactive = checkuser.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        if(ifactive == 1 ) {
            Intent myIntent = new Intent(LoginActivity.this, patientIDActivity.class);
            LoginActivity.this.startActivity(myIntent);
            finish();
        }else{
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("wrong password or username");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
