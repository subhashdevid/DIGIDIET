package edu.motibagh.digidiet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class LaunchActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 1800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        //setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             Intent i = new Intent(LaunchActivity.this,SelectionOption.class);
             //Intent is used to switch from one activity to another.
             startActivity(i);
             //invoke the SecondActivity.
             finish();
             //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}