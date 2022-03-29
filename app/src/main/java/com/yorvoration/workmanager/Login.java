package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {
    FirebaseFirestore db;
    private FirebaseAuth auth;
    private SqlData MyDb;

    Intent intent;
    private EditText edilogemail,edilogpassword;
    private ProgressBar progressBarlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        MyDb = new SqlData(this);

        Button btnsubmit = findViewById(R.id.btnsubmit);
        edilogemail = findViewById(R.id.edilogemail);
        edilogpassword = findViewById(R.id.edilogpassword);
        TextView txtlogregstration = findViewById(R.id.txtlogregstration);
        TextView txtresetpassword = findViewById(R.id.txtresetpassword);
        progressBarlog = findViewById(R.id.progressBarlog);

        progressBarlog.setVisibility(View.GONE);
        txtlogregstration.setOnClickListener(view -> {
            intent = new Intent(Login.this,Regstartion.class);
            startActivity(intent);
        });
        btnsubmit.setOnClickListener(view -> {
            progressBarlog.setVisibility(View.VISIBLE);
            String email = edilogemail.getText().toString();
            final String password = edilogpassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                edilogemail.setError(getString(R.string.empty));
                return;
            }

            if (TextUtils.isEmpty(password)) {
                edilogpassword.setError(getString(R.string.empty));
                return;
            }
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, task -> {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                progressBarlog.setVisibility(View.GONE);
                                edilogpassword.setError("kam");
                            } else {
                                progressBarlog.setVisibility(View.GONE);
                                edilogpassword.setError(getString(R.string.error));
                                edilogemail.setError(getString(R.string.error));
                                Toast.makeText(Login.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (password.length() < 6) {
                                edilogpassword.setError("kam");
                            } else {
                                String userid1 = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                //textView2.setText(userid1);
                                String til = "en";
                                String rejim = "rejim";
                                String kalit = "1";
                                String parol = edilogpassword.getText().toString();
                                Boolean result = MyDb.kiritish(userid1, til, rejim, kalit, parol);
                                progressBarlog.setVisibility(View.GONE);
                                if (result == true) {
                                    intent = new Intent(Login.this, Sample.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
        });

    }
}
