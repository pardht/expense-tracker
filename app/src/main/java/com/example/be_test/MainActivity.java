package com.example.be_test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView lvTransactions;
    Button btnInput;
    private ArrayList<Transaction> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvTransactions = findViewById(R.id.list_transactions);
        btnInput = findViewById(R.id.btn_input);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input();
            }
        });
    }

    private void input() {
        Intent i = new Intent(this, InputActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT `amount`, `description`, `date`, `type` FROM `transaction` WHERE 1";
        Cursor c = db.rawQuery(sql, new String[0]);

        //ambil data dari dtbsase
        list.clear();
        while(c.moveToNext()) {
            int amount = c.getInt(0);
            String description = c.getString(1);
            String date = c.getString(2);
            int type = c.getInt(3);

            Transaction t = new Transaction(amount, description, date, type);
            list.add(t);
        }

        //update ke list bang
        lvTransactions.setAdapter(new ArrayAdapter<Transaction>(this,
                android.R.layout.simple_list_item_1, list));
    }


}