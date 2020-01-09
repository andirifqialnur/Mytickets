package com.digitalsoftware.tiketonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegisterAct extends AppCompatActivity {

    Animation app_splash, btt, ttb;
    ImageView ic_sc;
    TextView txt_sc, subtxt_sc;
    Button btn_explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        ic_sc = findViewById(R.id.ic_sc);
        txt_sc = findViewById(R.id.txt_sc);
        subtxt_sc = findViewById(R.id.subtxt_sc);
        btn_explore = findViewById(R.id.btn_explore);

        btn_explore.startAnimation(btt);
        ic_sc.startAnimation(app_splash);
        txt_sc.startAnimation(ttb);
        subtxt_sc.startAnimation(ttb);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessRegisterAct.this, HomeAct.class);
                startActivity(gotohome);
            }
        });

    }
}
