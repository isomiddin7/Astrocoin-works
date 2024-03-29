package com.yorvoration.workmanager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    Handler handler;
    DocumentReference documentReference;
    FirebaseFirestore db;
    String programm_version = "1.0", kalit = "null";
    private SqlData MyDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        MyDb = new SqlData(this);
        oqishsh();
        handler = new Handler();

        handler.postDelayed(() -> {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
                documentReference = db.document("Admin/admin");
                documentReference.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String update = documentSnapshot.getString("UPDATE");
                        if (Objects.equals(update, programm_version)) {
                            if (Objects.equals(kalit, "1")) {
                                intent = new Intent(MainActivity.this, Password.class);
                            } else {
                                if (Objects.equals(kalit, "")) {
                                    intent = new Intent(MainActivity.this, Password.class);
                                } else {
                                    intent = new Intent(MainActivity.this, Login.class);
                                }
                            }
                            startActivity(intent);
                            finish();
                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle(getString(R.string.update)).setMessage(getString(R.string.dasturni_yangilang))
                                    .setPositiveButton(getString(R.string._ha), (dialog, which) -> {
                                      /*  Uri uri = Uri.parse("http://t.me/Partner");
                                        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                                        likeIng.setPackage("com.telegram.android");
                                        try {
                                            startActivity(likeIng);
                                        } catch (ActivityNotFoundException e) {
                                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse("http://t.me/Partner")));
                                        }*/
                                    }).setNegativeButton(getString(R.string._yoq), (dialogInterface, i) -> finish()).show();
                        }
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, getString(R.string.dasturd_xatolik), Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.connect)).setMessage("internetga ulaning")
                        .setPositiveButton(getString(R.string._ha),
                                (dialog, which) -> {
                                    @SuppressLint("WifiManagerLeak")
                                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                    wifi.setWifiEnabled(true);
                                }).setNegativeButton(getString(R.string._yoq), (dialogInterface, i) -> finish()).show();
                Toast.makeText(MainActivity.this, "internet o`chiq", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }
    private void oqishsh() {
        Cursor res = MyDb.oqish();
        StringBuilder stringBuffer = new StringBuilder();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append(res.getString(4));
            }
            kalit = stringBuffer.toString();
        }
    }
}