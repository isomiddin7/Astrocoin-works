package com.yorvoration.workmanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sample extends AppCompatActivity {
    private SqlData MyDb;
    private FirebaseFirestore db;
    TextView txtsamname,txtsamid,txtastrocoin;
    private SwipeRefreshLayout swipeRefreshLayout;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);
        MyDb = new SqlData(this);
        db = FirebaseFirestore.getInstance();

        txtsamname = findViewById(R.id.txtsamname);
        txtsamid = findViewById(R.id.txtsamid);
        txtastrocoin = findViewById(R.id.txtastrocoin);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        oqishsh();
        readfirebase();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            readfirebase();
            swipeRefreshLayout.setRefreshing(false);
        });

    }
    private void readfirebase(){
        DocumentReference documentReference1 = db.document("User/"+uid);
        documentReference1.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String name = documentSnapshot.getString("ISMI");
                String id = documentSnapshot.getString("USERID");
                String coin = documentSnapshot.getString("ASTROCOIN");

                txtastrocoin.setText(coin);
                txtsamname.setText(name);
                txtsamid.setText(id);

            }
        });
    }
    private void oqishsh() {
        Cursor res = MyDb.oqish();
        StringBuilder stringBuffer = new StringBuilder();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append(res.getString(1));

            }
            uid = stringBuffer.toString();
        }
    }
}
