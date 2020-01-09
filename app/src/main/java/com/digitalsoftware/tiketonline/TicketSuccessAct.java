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

public class TicketSuccessAct extends AppCompatActivity {

    Animation app_splash, btt, ttb;
    ImageView image_suc;
    TextView suc_title, suc_subtitle;
    Button btn_view_ticket, btn_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_success);

        image_suc = findViewById(R.id.image_suc);
        suc_title = findViewById(R.id.suc_title);
        suc_subtitle = findViewById(R.id.suc_subtitle);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        btn_dashboard = findViewById(R.id.btn_dashboard);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        btn_dashboard.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);
        suc_subtitle.startAnimation(ttb);
        suc_title.startAnimation(ttb);
        image_suc.startAnimation(app_splash);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(TicketSuccessAct.this, MyProfile.class);
                startActivity(gotoprofile);
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(TicketSuccessAct.this, HomeAct.class);
                startActivity(gotohome);
            }
        });

    }
}
