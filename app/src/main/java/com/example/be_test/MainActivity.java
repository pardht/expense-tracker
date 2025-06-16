package com.example.be_test;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView lvTransactions;
    private TextView tvTotal, tvRevenue, tvExpense;
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

        tvTotal = findViewById(R.id.tv_total);
        tvRevenue = findViewById(R.id.tv_revenue);
        tvExpense = findViewById(R.id.tv_expense);
        btnInput = findViewById(R.id.btn_input);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input();
            }
        });
        lvTransactions = findViewById(R.id.list_transactions);
        lvTransactions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemlongclick(i);
                return false;
         }
        });
    }

    private void itemlongclick(int i) {
        Transaction t = list.get(i);

        String[] menu = new String[] {"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("apa loe liat liat");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    edit(t);
                } else if (which == 1) {
                    delete(t);
                }
            }
        });
    builder.create().show();
    }

    private void delete(Transaction t) {
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "DELETE FROM `transaction` WHERE `id` = ?";
        db.execSQL(sql, new Object[] {t.getId()});

        refresh();
    }
    private void edit(Transaction t) {
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

        String sql = "SELECT `id`, `amount`, `description`, `date`, `type` FROM `transaction` WHERE 1";
        Cursor c = db.rawQuery(sql, new String[0]);

        //ambil data dari database
        list.clear();
        while(c.moveToNext()) {
            int id = c.getInt(0);
            int amount = c.getInt(1);
            String description = c.getString(2);
            String date = c.getString(3);
            int type = c.getInt(4);

            Transaction t = new Transaction(id, amount, description, date, type);
            list.add(t);
        }

        //update ke list bang
        lvTransactions.setAdapter(new ArrayAdapter<Transaction>(this,
                android.R.layout.simple_list_item_1, list));

        String sqlRevenue = "SELECT SUM(`amount`) AS total_revenue FROM `transaction` WHERE `type` = 1;";
        String sqlExpense = "SELECT SUM(`amount`) AS total_expense FROM `transaction` WHERE `type` = 0;";
        Cursor totalRev = db.rawQuery(sqlRevenue, null);
        Cursor totalExp = db.rawQuery(sqlExpense, null);


        int totalRevenue = 0;
        if (totalRev.moveToFirst()) {
            totalRevenue = totalRev.getInt(totalRev.getColumnIndexOrThrow("total_revenue"));
        }
        totalRev.close();
        tvRevenue.setText(String.valueOf("$"+totalRevenue));

        int totalExpense = 0;
        if (totalExp.moveToFirst()) {
            totalExpense = totalExp.getInt(totalExp.getColumnIndexOrThrow("total_expense"));
        }
        totalExp.close();
        tvExpense.setText(String.valueOf("$-"+totalExpense));

        int balance = totalRevenue - totalExpense;
        tvTotal.setText(String.valueOf("$"+balance));

    }


}