package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Reg5 extends AppCompatActivity {

    DocumentReference documentReference;
    FirebaseFirestore db;
    private FirebaseAuth auth;
    private SqlData MyDb;
    Intent intent;

    private Button btnreg5submit;
    private ImageView imgreg5;
    String nikname,gender = "Man",userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg5);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        MyDb = new SqlData(this);

        btnreg5submit = findViewById(R.id.btnreg5submit);
        imgreg5 = findViewById(R.id.imgreg5);
        View wievmale1 = findViewById(R.id.wievmale1);
        View wievmale2 = findViewById(R.id.wievmale2);
        View wievfemale1 = findViewById(R.id.wievfemale1);
        View wievfemale2 = findViewById(R.id.wievfemale2);
        TextView textView15 = findViewById(R.id.textView15);

        wievmale1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wievmale1.setVisibility(View.VISIBLE);
                wievfemale1.setVisibility(View.VISIBLE);
                wievmale2.setVisibility(View.INVISIBLE);
                wievfemale2.setVisibility(View.INVISIBLE);
                gender = "Man";
            }
        });
        wievmale2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wievmale1.setVisibility(View.VISIBLE);
                wievfemale1.setVisibility(View.VISIBLE);
                wievmale2.setVisibility(View.INVISIBLE);
                wievfemale2.setVisibility(View.INVISIBLE);
                gender = "Woman";
            }
        });
        wievfemale1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wievmale1.setVisibility(View.INVISIBLE);
                wievfemale1.setVisibility(View.INVISIBLE);
                wievmale2.setVisibility(View.VISIBLE);
                wievfemale2.setVisibility(View.VISIBLE);
            }
        });
        wievfemale2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wievmale1.setVisibility(View.VISIBLE);
                wievfemale1.setVisibility(View.VISIBLE);
                wievmale2.setVisibility(View.INVISIBLE);
                wievfemale2.setVisibility(View.INVISIBLE);
            }
        });



        imgreg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            nikname = (String) b.get("name");
            Toast.makeText(this, ""+nikname, Toast.LENGTH_SHORT).show();
        }

        btnreg5submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] values = nikname.split(",");
                String name = values[0];
                String surname = values[1];
                String data = values[2];
                String email = values[3];
                String password = values[4];
                //textView15.setText(name+"-"+surname+"-"+data+"-"+email+"-"+password+"-");
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Reg5.this, task -> {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Reg5.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            } else {
                                //sendVerificationEmail();
                                userid = auth.getCurrentUser().getUid();
                                int min = 1000;
                                int max = 9999;
                                int diff = max - min;
                                Random random = new Random();
                                int i = random.nextInt(diff + 1);
                                i += min;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
                                String time = sdf.format(new Date());
                                String son = String.valueOf(i);
                                Map<String, Object> user1 = new HashMap<>();
                                user1.put("ISMI", name);
                                user1.put("FAMILYA", surname);
                                user1.put("PAROL", password);
                                user1.put("POCHTA", email);
                                user1.put("YOSHI", data);
                                user1.put("ASTROCOIN", "0");
                                user1.put("DARAJA", "0");
                                user1.put("USERID", son);
                                user1.put("UID", userid);
                                user1.put("PHOTO", "0");
                                user1.put("SONGIFAOLLIK", time);
                                user1.put("KIRGANVAQT", time);
                                String til = "en";
                                String rejim = "rejim";
                                String kalit = "1";
                                Boolean result = MyDb.kiritish(userid, til, rejim, kalit, password);
                                db.collection("User").document(userid).set(user1)
                                        .addOnSuccessListener(unused -> {
                                            Intent intent = new Intent(Reg5.this,Sample.class);
                                            startActivity(intent);
                                            finish();
                                        });
                            }
                        });
            }
        });

    }
}
