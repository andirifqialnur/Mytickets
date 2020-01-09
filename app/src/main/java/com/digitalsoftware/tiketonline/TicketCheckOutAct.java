package com.digitalsoftware.tiketonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBBlackValueNode;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckOutAct extends AppCompatActivity {

    ImageView btnplus, btnminus, notice_money;
    Button btn_buy_ticket;
    Integer valueticket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargaticket = 0;
    Integer sisa_balance = 0;

    TextView textjumlahticket, txttotalharga, txtmybalance, nama_wisata, lokasi, ketentuan;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "username_key";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";

    //generate nomor integer secara random
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check_out);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btnplus = findViewById(R.id.btnplus);
        btnminus = findViewById(R.id.btnminus);
        textjumlahticket = findViewById(R.id.textjumlahticket);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);

        txttotalharga = findViewById(R.id.txttotalharga);
        txtmybalance = findViewById(R.id.txtmybalance);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        notice_money = findViewById(R.id.notice_money);

        textjumlahticket.setText(valueticket.toString());

        // mengilangkan button minus secara default
        btnminus.animate().alpha(0).setDuration(300);
        btnminus.setEnabled(false);

        notice_money.setVisibility(View.GONE);

        //mengambil data balance dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                txtmybalance.setText("US$ " + mybalance + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mengambil database
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("ketentuan").getValue().toString();
                time_wisata = dataSnapshot.child("ketentuan").getValue().toString();

                valuehargaticket = Integer.valueOf(dataSnapshot.child("harga_ticket").getValue().toString());
                valuetotalharga = valuehargaticket * valueticket;
                txttotalharga.setText("US$ " + valuetotalharga + "");
//                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueticket += 1;
                textjumlahticket.setText(valueticket.toString());

                if (valueticket > 1) {
                    btnminus.animate().alpha(1).setDuration(300);
                    btnminus.setEnabled(true);
                }
                valuetotalharga = valuehargaticket * valueticket;
                txttotalharga.setText("US$ " + valuetotalharga + "");

                if (valuetotalharga > mybalance) {
                    btn_buy_ticket.animate().translationY(250)
                            .alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    txtmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_money.setVisibility(View.VISIBLE);
                }
            }
        });

        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueticket -= 1;
                textjumlahticket.setText(valueticket.toString());

                if (valueticket < 2) {
                    btnminus.animate().alpha(0).setDuration(300);
                    btnminus.setEnabled(false);
                }
                valuetotalharga = valuehargaticket * valueticket;
                txttotalharga.setText("US$ " + valuetotalharga + "");

                if (valuetotalharga < mybalance) {
                    btn_buy_ticket.animate().translationY(0)
                            .alpha(1).setDuration(350).start();
                    btn_buy_ticket.setEnabled(true);
                    txtmybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_money.setVisibility(View.GONE);
                }
            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //menyimpan data user pada firebase dan membuat table baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets")
                        .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket")
                                 .setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueticket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gototicketsuccess = new Intent(TicketCheckOutAct.this, TicketSuccessAct.class);
                        startActivity(gototicketsuccess);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //update sisa balance di database
                //mengambil data balance dari firebase
                reference4   = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}
