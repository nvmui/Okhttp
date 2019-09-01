package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityAddsach extends AppCompatActivity {
    EditText editMaSach, editTenSach, editTenTG, editNXB, editNamXB;
    Button btnAdd, btnExit;
    ListView lvSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsach);
        AnhXa();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        editMaSach=(EditText) findViewById(R.id.editMaSach);
        editTenSach=(EditText) findViewById(R.id.editTenSach);
        editTenTG=(EditText) findViewById(R.id.editTacGia);
        editNXB=(EditText) findViewById(R.id.editNhaXB);
        editNamXB=(EditText) findViewById(R.id.editNamXB);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnExit=(Button)findViewById(R.id.btnExit);
        lvSach=(ListView)findViewById(R.id.listBook);
    }
}
