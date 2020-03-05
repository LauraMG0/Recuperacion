package com.example.recuperacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnNoteListener{
    private MyDB db;
    private MyDatabaseHelper helper;
    private AlertDialog.Builder builder;
    private RecyclerView rv;
    private ArrayList<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recyclerView);
        db = new MyDB(this);
        helper = new MyDatabaseHelper(this);
        helper.onCreate(db.getDatabase());
        db.insert();
        setAdapter();
        dialog();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show();

            }
        });
    }

    public void setAdapter() {
        Cursor c = db.selectRecords();
        list = new ArrayList<Product>();
        if (c.getCount() > 0){
            do {
                list.add(new Product(c.getInt(0),c.getString(1),c.getString(2)));
            }while(c.moveToNext());
        }
        RecyclerAdapter adapter = new RecyclerAdapter(list,this,this,c);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void dialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new product");

        final EditText input = new EditText(this);
        final EditText input2 = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        input.setHint("Name");
        layout.addView(input);

        input2.setHint("Number");
        layout.addView(input2);

        builder.setView(layout);

        builder.setPositiveButton("Add product", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.createRecords(0,input.getText().toString(),input2.getText().toString());
                setAdapter();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
    public void delete(int s){
        db.delete(s);
        setAdapter();
    }

    public void edit(int s){
        Intent i = new Intent(this,ProductDetail.class);
        i.putExtra("id", list.get(s).getId());
        i.putExtra("name",list.get(s).getName());
        i.putExtra("quantity", list.get(s).getQuantity());
        startActivityForResult(i,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            int id = data.getIntExtra("id",0);
            String name = data.getStringExtra("NAME");
            String number = data.getStringExtra("QUANTITY");
            db.update(id,name, number);
            setAdapter();
        }
    }

    @Override
    public void onNoteClick(int position) {
        edit(position);
    }
}
