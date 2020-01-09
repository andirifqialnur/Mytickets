package com.digitalsoftware.tiketonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    Animation app_splash, btt;
    ImageView app_lg_sp;
    TextView app_tl_sp;

    String USERNAME_KEY = "username_key";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //find element
        app_lg_sp = findViewById(R.id.app_lg_sp);
        app_tl_sp = findViewById(R.id.app_tl_sp);

        //run animation
        app_lg_sp.startAnimation(app_splash);
        app_tl_sp.startAnimation(btt);

        getUsernameLocal();

    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

        if (username_key_new.isEmpty()) {

            Handler hndlr = new Handler();
            hndlr.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gogostarted = new Intent(SplashAct.this, GetStartedAct.class);
                    startActivity(gogostarted);
                    finish();
                }
            },2000);

        }

        else {

            Handler hndlr = new Handler();
            hndlr.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gogethome = new Intent(SplashAct.this, HomeAct.class);
                    startActivity(gogethome);
                    finish();
                }
            },2000);

        }
    }

}
