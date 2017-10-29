package com.danman.harsh.esphome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import Util.Connection_Stat;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Connection_Stat conn = new Connection_Stat();
        if(!conn.getConnectStat(getApplicationContext()))
        {
            Toast.makeText(getApplicationContext(), "Please make sure you're connected to the internet!", Toast.LENGTH_LONG).show();
            this.finish();
            return;
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i); //Start the activity
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
    }, SPLASH_TIME_OUT);
    }
}
