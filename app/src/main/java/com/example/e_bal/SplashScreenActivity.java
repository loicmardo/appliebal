package com.example.e_bal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import  android.os.Handler ;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // REdigirer vers la page principale apres 3 secondes

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // demarrer  une page
                Intent intent = new Intent(getApplicationContext(),firstActivity.class);
                startActivity(intent);
                finish();
            }
        };
        //Handler post delayed
        new  Handler().postDelayed(runnable,4000);
    }
}