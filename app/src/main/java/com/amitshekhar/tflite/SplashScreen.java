package com.amitshekhar.tflite;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
sharedpreferenceconfig sf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sf=new sharedpreferenceconfig(this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchMainActivity();
            }
        }, 3000);
    }
    public void launchMainActivity(){
        Intent intent=null;
        if(sf.readloginstatus()==false)
        intent = new Intent(getApplicationContext(), Register.class);
        else
            intent = new Intent(getApplicationContext(), decision.class);
        startActivity(intent);
        finish();
    }
}
