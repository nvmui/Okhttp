package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityUpdatetacgia extends AppCompatActivity {
    EditText editMaTG, editTenTG, editDiaChiTG, editEmail, editSoDienThoaiTG;
    Button btn_UpdateTG, btn_ExitTG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatetacgia);
        Intent intent = getIntent();
        TacGia tacGia = (TacGia) intent.getSerializableExtra("tacgia");//lấy dữ liệu về
        AnhXa();
        final String matg = tacGia.MaTG;
        editMaTG.setText(tacGia.MaTG);
        editTenTG.setText(tacGia.TenTacGia);
        editDiaChiTG.setText(tacGia.DiaChi);
        editEmail.setText(tacGia.Email);
        editSoDienThoaiTG.setText(tacGia.DienThoai);
        btn_UpdateTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentg = editTenTG.getText().toString().trim();
                String diachi = editDiaChiTG.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String sodt = editSoDienThoaiTG.getText().toString().trim();
                new updateTacGia(matg, tentg, diachi, email, sodt).execute(new config().urlphp+"updateTacGia.php");
                finish();
                Intent intent = new Intent(ActivityUpdatetacgia.this, ActivityTacgia.class);
                startActivity(intent);
            }
        });
        btn_ExitTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        editMaTG = (EditText) findViewById(R.id.editMaTG);
        editTenTG = (EditText) findViewById(R.id.editTenTG);
        editDiaChiTG = (EditText) findViewById(R.id.editDiaChiTG);
        editEmail = (EditText) findViewById(R.id.editEmailTG);
        editSoDienThoaiTG = (EditText) findViewById(R.id.editDienThoaiTG);
        btn_UpdateTG = (Button) findViewById(R.id.btnAddTG);
        btn_ExitTG = (Button) findViewById(R.id.btnExitTG);
    }

    //Class update tac gia
    class updateTacGia extends AsyncTask<String, Void, String> {
        String matg, tentg, diachitg, emailtg, sodttg;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        public updateTacGia(String matg, String tentg, String diachitg, String emailtg, String sodttg) {
            this.matg = matg;
            this.tentg = tentg;
            this.diachitg = diachitg;
            this.emailtg = emailtg;
            this.sodttg = sodttg;
        }

        @Override
        protected String doInBackground(String... strings) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("MaTG", matg)
                    .addFormDataPart("TenTacGia", tentg)
                    .addFormDataPart("DiaChi", diachitg)
                    .addFormDataPart("Email", emailtg)
                    .addFormDataPart("DienThoai", sodttg)
                    .setType(MultipartBody.FORM)
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .method("POST", requestBody)
                    .post(requestBody)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
