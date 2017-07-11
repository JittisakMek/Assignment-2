package com.egci428.assignment2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static EditText USER_NAME, USER_PASS;
    private ProfileDataSource dataSource;
    boolean loginstatus = false;
    Cursor CR;

    public static String username, userpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        dataSource = new ProfileDataSource(this);
        dataSource.open();
    }

    public void signInButtonListener(View v){

        USER_NAME = (EditText)findViewById(R.id.userTxt);
        USER_PASS = (EditText)findViewById(R.id.passTxt);
        username = USER_NAME.getText().toString();
        userpass = USER_PASS.getText().toString();

        CR = dataSource.getInformation(dataSource);
        CR.moveToFirst();


            //Whenever you are dealing with Cursors, ALWAYS check for null and check for moveToFirst() without fail.
            if( CR != null && CR.moveToFirst() ){
                do{

                    if(username.equals(CR.getString(1)) && userpass.equals(CR.getString(2))){
                        loginstatus = true;
                    }

                }while(CR.moveToNext());
            }
            else{
                Toast.makeText(getBaseContext(), "No accounts yet, please sign up", Toast.LENGTH_SHORT ).show();
            }


            if(loginstatus == true){
            Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_SHORT ).show();

            Intent intent = new Intent(MainActivity.this, MainPage.class);
            startActivity(intent);
            loginstatus = false;

        }else{
            Toast.makeText(getBaseContext(), "Login Fail", Toast.LENGTH_SHORT ).show();
        }

    }

    public void cancelButtonListener(View v){
        USER_NAME.setText("");
        USER_PASS.setText("");
    }

    public void signUpButtonListener(View v){
        Intent intent = new Intent(MainActivity.this, Sign_up.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        if (requestCode == 0) {

        }
    }

}
