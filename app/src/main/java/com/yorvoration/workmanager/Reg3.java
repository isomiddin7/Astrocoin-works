package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Reg3 extends AppCompatActivity {
    String nikname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg3);

        EditText edireg3email = findViewById(R.id.edireg3email);
        ImageView imgreg3 = findViewById(R.id.imgreg3);
        Button btnreg3submit = findViewById(R.id.btnreg3submit);

        imgreg3.setOnClickListener(new View.OnClickListener() {
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

        btnreg3submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edireg3email.getText().toString().isEmpty()){
                    edireg3email.setError(getString(R.string.error));
                    return;
                }
                String a = nikname+","+edireg3email.getText().toString();
                Intent ii = new Intent(Reg3.this, Reg4.class);
                ii.putExtra("name", a);
                startActivity(ii);
            }
        });
    }
}
