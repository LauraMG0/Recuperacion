package com.example.recuperacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProductDetail extends AppCompatActivity {
    private EditText ed1, ed2;
    private Button b;
    private String name, number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ed1 = findViewById(R.id.editText);
        ed2 = findViewById(R.id.editText2);
        b = findViewById(R.id.button);
        name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("quantity");
        ed1.setText(name);
        ed2.setText(number);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    public void save() {
        name = ed1.getText().toString();
        number = ed2.getText().toString();
        Intent i = new Intent();
        i.putExtra("ID",getIntent().getIntExtra("id",0));
        i.putExtra("NAME", ed1.getText().toString());
        i.putExtra("QUANTITY", ed2.getText().toString());
        setResult(0,i);
        finish();
    }

}
