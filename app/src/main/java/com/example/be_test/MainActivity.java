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

    private EditText inpJmlPengeluaran, inpDescPengeluaran, inpTglPengeluaran;
    private Button btnInpPengeluaran;
    private ListView lvTransactions;
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

        btnInpPengeluaran = findViewById(R.id.btn_inp_pengeluaran);
        inpJmlPengeluaran = findViewById(R.id.inp_jml_pengeluaran);
        inpDescPengeluaran = findViewById(R.id.inp_desc_pengeluaran);
        inpTglPengeluaran = findViewById(R.id.inp_tgl_pengeluaran);

        btnInpPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePengeluaran();
            }
        });

        lvTransactions = findViewById(R.id.list_transactions);

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

    //    class pengeluaran
    public class Pengeluaran {
        private String jumlah;
        private String deskripsi;
        private String tanggal;

        // Constructor
        public Pengeluaran(String jumlah, String deskripsi, String tanggal) {
            this.jumlah = jumlah;
            this.deskripsi = deskripsi;
            this.tanggal = tanggal;
        }
        // Getter untuk mengambil data
        public String getJumlah() {
            return jumlah;
        }
        public String getDeskripsi() {
            return deskripsi;
        }
        public String getTanggal() {
            return tanggal;
        }
        @Override
        public String toString() {
            return "Pengeluaran:\nJumlah: " + jumlah + "\nDeskripsi: " + deskripsi + "\nTanggal: " + tanggal;
        }
    }

    private void savePengeluaran() {
        // Ambil data dari input
        String jumlah = inpJmlPengeluaran.getText().toString().trim();
        String deskripsi = inpDescPengeluaran.getText().toString().trim();
        String tanggal = inpTglPengeluaran.getText().toString().trim();

        // Cek apakah input kosong
        if (jumlah.isEmpty() || deskripsi.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat objek Pengeluaran
        Pengeluaran pengeluaran = new Pengeluaran(jumlah, deskripsi, tanggal);

        // Kirim objek ke aktivitas lain menggunakan Intent
        Intent intentPengeluaran = new Intent(MainActivity.this, ShowPengeluaranActivity.class);
        intentPengeluaran.putExtra("jumlah", pengeluaran.getJumlah());
        intentPengeluaran.putExtra("deskripsi", pengeluaran.getDeskripsi());
        intentPengeluaran.putExtra("tanggal", pengeluaran.getTanggal());

        // Tampilkan toast dengan informasi pengeluaran
        Toast.makeText(this, pengeluaran.toString(), Toast.LENGTH_LONG).show();

        // Mulai aktivitas baru
        startActivity(intentPengeluaran);
    }

}