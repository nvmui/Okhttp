package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityAddtacgia extends AppCompatActivity {
    EditText editMaTG, editTenTG, editDiaChiTG, editEmail, editSoDienThoaiTG;
    Button btn_AddTG, btn_ExitTG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtacgia);
        AnhXa();
        btn_AddTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matg=editMaTG.getText().toString().trim();
                String tentg=editTenTG.getText().toString().trim();
                String diachi=editDiaChiTG.getText().toString().trim();
                String email=editEmail.getText().toString().trim();
                String dienthoai=editSoDienThoaiTG.getText().toString().trim();
                new postTacGia(matg,tentg,diachi,email,dienthoai).execute("http://192.168.26.111/apiqltv/insertTacGia.php");
                finish();
                Intent intent =new Intent(ActivityAddtacgia.this,ActivityTacgia.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        editMaTG = (EditText) findViewById(R.id.editMaTG);
        editTenTG = (EditText) findViewById(R.id.editTenTG);
        editDiaChiTG = (EditText) findViewById(R.id.editDiaChiTG);
        editEmail = (EditText) findViewById(R.id.editEmailTG);
        editSoDienThoaiTG = (EditText) findViewById(R.id.editDienThoaiTG);
        btn_AddTG = (Button) findViewById(R.id.btnAddTG);
        btn_ExitTG = (Button) findViewById(R.id.btnExitTG);
    }

    class postTacGia extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        String matg, tentg, diachi, email, dienthoai;

        public postTacGia(String matg, String tentg, String diachi, String email, String dienthoai) {
            this.matg = matg;
            this.tentg = tentg;
            this.diachi = diachi;
            this.email = email;
            this.dienthoai = dienthoai;
        }

        @Override
        protected String doInBackground(String... strings) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("MaTG", matg)
                    .addFormDataPart("TenTacGia", tentg)
                    .addFormDataPart("DiaChi", diachi)
                    .addFormDataPart("Email", email)
                    .addFormDataPart("DienThoai", dienthoai)
                    .setType(MultipartBody.FORM)
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .build();
//
//
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody body = RequestBody.create(mediaType, "{\r\n\t\"MaTG\": \"3\",\r\n    \"TenTacGia\": \"Nguyễn Văn An\",\r\n    \"DiaChi\": \"143 Nguyễn Lương Bằng\",\r\n    \"Email\": \"vanan@gmail.com\",\r\n    \"DienThoai\": \"0905873735\"\r\n}");
//            Request request = new Request.Builder()
//                    .url("192.168.26.111/apiqltv/insertTacGia.php")
//                    .method("POST", body)
//                    .addHeader("Content-Type", "application/json")
//                    .build();
//            Response response = client.newCall(request).execute();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Thông báo lỗi", s);
            super.onPostExecute(s);
        }
    }
}
