package com.digitalsoftware.tiketonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_lay_pisa, btn_lay_torri, btn_lay_pagoda,
            btn_lay_candi, btn_lay_spinx, btn_lay_monas;
    ImageView photo_home_user;
    TextView nama_lengkap, user_balance, bio;

    DatabaseReference reference;

    String USERNAME_KEY = "username_key";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_lay_pisa = findViewById(R.id.btn_lay_pisa);
        btn_lay_torri = findViewById(R.id.btn_lay_torri);
        btn_lay_pagoda = findViewById(R.id.btn_lay_pagoda);
        btn_lay_candi = findViewById(R.id.btn_lay_candi);
        btn_lay_spinx = findViewById(R.id.btn_lay_spinx);
        btn_lay_monas = findViewById(R.id.btn_lay_monas);

        photo_home_user = findViewById(R.id.photo_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        user_balance = findViewById(R.id.user_balance);
        bio = findViewById(R.id.bio);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                user_balance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());

                Picasso.with(HomeAct.this)
                        .load(dataSnapshot.child("url_photo_profile")
                                .getValue().toString()).centerCrop().fit()
                        .into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        photo_home_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoprofile = new Intent(HomeAct.this, MyProfile.class);
                startActivity(gotoprofile);

            }
        });

        btn_lay_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gototicketpisa = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicketpisa.putExtra("jenis_tiket", "Pisa");
                startActivity(gototicketpisa);
            }
        });

        btn_lay_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gototickettorri = new Intent(HomeAct.this, TicketDetailAct.class);
                gototickettorri.putExtra("jenis_tiket", "Torri");
                startActivity(gototickettorri);
            }
        });

        btn_lay_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gototicketpagoda = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicketpagoda.putExtra("jenis_tiket", "Pagoda");
                startActivity(gototicketpagoda);
            }
        });

        btn_lay_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gototicketcandi = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicketcandi.putExtra("jenis_tiket", "Candi");
                startActivity(gototicketcandi);
            }
        });

        btn_lay_spinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gototicketspinx = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicketspinx.putExtra("jenis_tiket", "Spinx");
                startActivity(gototicketspinx);
            }
        });

        btn_lay_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gototicketmonas = new Intent(HomeAct.this, TicketDetailAct.class);
                gototicketmonas.putExtra("jenis_tiket", "Monas");
                startActivity(gototicketmonas);
            }
        });

    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
