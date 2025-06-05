package com.example.be_test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowPengeluaranActivity extends AppCompatActivity {

    private TextView showJmlPengeluaran, showDescPengeluaran, showTglPengeluaran;
    private Button btnFormPengeluaran;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_pengeluaran);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnFormPengeluaran = findViewById(R.id.btn_form_pengeluaran);
        showJmlPengeluaran = findViewById(R.id.show_jml_pengeluaran);
        showDescPengeluaran = findViewById(R.id.show_desc_pengeluaran);
        showTglPengeluaran = findViewById(R.id.show_tgl_pengeluaran);

        btnFormPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });

        Intent intentPengeluaran = getIntent();
        String jumlah = intentPengeluaran.getStringExtra("jumlah");
        String deskripsi = intentPengeluaran.getStringExtra("deskripsi");
        String tanggal = intentPengeluaran.getStringExtra("tanggal");

        showJmlPengeluaran.setText(jumlah);
        showDescPengeluaran.setText(deskripsi);
        showTglPengeluaran.setText(tanggal);

    }
    private void goMainActivity() {
        Intent intent = new Intent(ShowPengeluaranActivity.this, MainActivity.class);
        startActivity(intent);
    }
}