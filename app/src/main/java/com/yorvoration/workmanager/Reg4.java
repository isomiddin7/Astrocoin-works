package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Reg4 extends AppCompatActivity {
    String nikname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg4);

        Button btnreg4submit = findViewById(R.id.btnreg4submit);
        EditText edireg4password = findViewById(R.id.edireg4password);
        ImageView imgreg4 = findViewById(R.id.imgreg4);

        imgreg4.setOnClickListener(new View.OnClickListener() {
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

        btnreg4submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edireg4password.getText().toString().isEmpty()){
                    edireg4password.setError(getString(R.string.error));
                    return;
                }
                if (edireg4password.getText().toString().length()<6){
                    edireg4password.setError(getString(R.string.error));
                    return;
                }
                String a = nikname+","+edireg4password.getText().toString();
                Intent ii = new Intent(Reg4.this, Reg5.class);
                ii.putExtra("name", a);
                startActivity(ii);
            }
        });

    }
}
