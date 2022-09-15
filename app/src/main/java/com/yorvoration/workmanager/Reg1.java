package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Reg1 extends AppCompatActivity {
    String nikname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg1);
        ImageView imgreg1 = findViewById(R.id.imgreg1);
        EditText edireg1name = findViewById(R.id.edireg1name);
        Button btnreg1submit = findViewById(R.id.btnreg1submit);


        imgreg1.setOnClickListener(new View.OnClickListener() {
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

        btnreg1submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = nikname+","+edireg1name.getText().toString();
                Intent ii = new Intent(Reg1.this, Reg2.class);
                ii.putExtra("name", a);
                startActivity(ii);
            }
        });
    }
}
