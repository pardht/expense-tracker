package com.example.be_test;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputActivity extends AppCompatActivity {
    private EditText inpJml, inpDesc, inpTgl;
    private RadioGroup rdGroup;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = sdf.format(calendar.getTime());

        btnSubmit = findViewById(R.id.btn_submit);
        inpJml = findViewById(R.id.inp_jml);
        inpDesc = findViewById(R.id.inp_desc);
        inpTgl = findViewById(R.id.inp_tgl);
        inpTgl.setText(todayDate);
        rdGroup = findViewById(R.id.rd_group);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();
            }
        });
    }

    private void saveTransaction() {
        String jml = "" + inpJml.getText();
        String desc = "" + inpDesc.getText();
        String tgl = "" + inpTgl.getText();

        int selectedId = rdGroup.getCheckedRadioButtonId();
        int type = -1; // default atau error value

        if (selectedId == R.id.rd_expense) {
            type = 0;  // Pengeluaran = 0
        } else if (selectedId == R.id.rd_revenue) {
            type = 1;  // Pemasukan = 1
        }

        int amount = Integer.parseInt(jml);
//        int type = Integer.parseInt(tipe);

        Transaction t = new Transaction(amount, desc, tgl, type);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String sql = "INSERT INTO `transaction`(`amount`, `description`, `date`, `type`) VALUES (?,?,?,?);";
        db.execSQL(sql, new Object[]{t.getAmount(), t.getDescription(), t.getDate(), t.getType()});

        finish();

    }
}