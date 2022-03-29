package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Reg2 extends AppCompatActivity {
    String nikname;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg2);

        Button btnreg2submit = findViewById(R.id.btnreg2submit);
        DatePicker picker = findViewById(R.id.datePicker1);
        ImageView imgreg2 = findViewById(R.id.imgreg2);

        imgreg2.setOnClickListener(new View.OnClickListener() {
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

        btnreg2submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = ""+picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
                String a = nikname+","+data;
                Intent ii = new Intent(Reg2.this, Reg3.class);
                ii.putExtra("name", a);
                startActivity(ii);
            }
        });


    }
}
