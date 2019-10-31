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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityUpdateSach extends AppCompatActivity {
    Button btnUpdate, btnExit;
    EditText editMaSach, editTenSach, editTenTacGia, editTenNXB, editNamXB;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sach);
        Intent intent = getIntent();
        Sach sach = (Sach) intent.getSerializableExtra("dataSach");
        AnhXa();
        id = sach.id;
        editMaSach.setText(sach.MaSach);
        editTenSach.setText(sach.TenSach);
        editTenTacGia.setText(sach.TenTacGia);
        editTenNXB.setText(sach.TenNXB);
        editNamXB.setText(sach.NamXB + "");
//        Toast.makeText(ActivityUpdateSach.this, "" + id, Toast.LENGTH_LONG).show();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach = editTenSach.getText().toString().trim();
                String tentacgia = editTenTacGia.getText().toString().trim();
                String tennxb = editTenNXB.getText().toString().trim();
                int namxb = Integer.parseInt(editNamXB.getText().toString().trim());
                new updateSach(tensach, tentacgia, tennxb, namxb).execute(new config().url+"saches/" + id);
                finish();
                Intent intent = new Intent(ActivityUpdateSach.this, Activitysach.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnExit = (Button) findViewById(R.id.btnThoat);
        editMaSach = (EditText) findViewById(R.id.editMaSach);
        editTenSach = (EditText) findViewById(R.id.editTenSach);
        editTenTacGia = (EditText) findViewById(R.id.editTacGia);
        editTenNXB = (EditText) findViewById(R.id.editNhaXB);
        editNamXB = (EditText) findViewById(R.id.editNamXB);
    }

    //
    class updateSach extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        String tensach, tentacgia, tennxb;
        int namxb;

        public updateSach(String tensach, String tentacgia, String tennxb, int namxb) {
            this.tensach = tensach;
            this.tentacgia = tentacgia;
            this.tennxb = tennxb;
            this.namxb = namxb;
        }
        @Override
        protected String doInBackground(String... strings) {
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject postdata = new JSONObject();
            try {
                postdata.put("TenSach", tensach);
                postdata.put("TenTacGia", tentacgia);
                postdata.put("TenNXB", tennxb);
                postdata.put("NamXB", namxb);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(mediaType, postdata.toString());
            Request request = new Request.Builder()
//                    .url("http://192.168.56.168:1337/Saches/")
                    .url(strings[0])
                    .method("PUT", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", new config().token)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    //call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String mMessage = response.body().string();
                    Log.e("update", mMessage);
                }
            });
            return null;
        }
    }

}
