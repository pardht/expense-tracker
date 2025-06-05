package com.example.be_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText inpJmlPengeluaran, inpDescPengeluaran, inpTglPengeluaran;
    private Button btnInpPengeluaran;
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