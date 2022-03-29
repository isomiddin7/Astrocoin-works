package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Regstartion extends AppCompatActivity {
    private EditText ediregname;
    private Button btnregsubmit;
    private ImageView imgreg;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regstration);

        btnregsubmit = findViewById(R.id.btnregsubmit);
        imgreg = findViewById(R.id.imgreg);
        ediregname = findViewById(R.id.ediregname);
        imgreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnregsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = ediregname.getText().toString();
                if (name.isEmpty()){
                    ediregname.setError(getString(R.string.error));
                    return;
                }
                Intent ii = new Intent(Regstartion.this, Reg1.class);
                ii.putExtra("name", name);
                startActivity(ii);
            }
        });

    }
}
