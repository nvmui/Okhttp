package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

public class ActivityAddsach extends AppCompatActivity {
    EditText editMaSach, editTenSach, editTenTG, editNXB, editNamXB;
    Button btnAdd, btnExit;
    ListView lvSach;
    config url;

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
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masach, tensach, tentacgia, tennxb;
                int namxb;
                masach = editMaSach.getText().toString().trim();
                tensach = editTenSach.getText().toString().trim();
                tentacgia = editTenTG.getText().toString().trim();
                tennxb = editNXB.getText().toString().trim();
                namxb = Integer.parseInt(editNamXB.getText().toString().trim());
                new AddSach(masach, tensach, tentacgia, tennxb, namxb).execute("http://192.168.26.111:1337/Saches/");
//                new postSach(masach, tensach, tentacgia, tennxb, namxb).execute("http://192.168.56.168/apiqltv/addSach.php");
//                XoaEditText();
                finish();
                Intent intent =new Intent(ActivityAddsach.this,Activitysach.class);
                startActivity(intent);
            }
        });
    }

    //Ánh xạ các biến
    private void AnhXa() {
        editMaSach = (EditText) findViewById(R.id.editMaSach);
        editTenSach = (EditText) findViewById(R.id.editTenSach);
        editTenTG = (EditText) findViewById(R.id.editTacGia);
        editNXB = (EditText) findViewById(R.id.editNhaXB);
        editNamXB = (EditText) findViewById(R.id.editNamXB);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnExit = (Button) findViewById(R.id.btnExit);
        lvSach = (ListView) findViewById(R.id.listBook);
    }

    //Xoá các EditText
    private void XoaEditText() {
        editMaSach.setText("");
        editTenSach.setText("");
        editTenTG.setText("");
        editNXB.setText("");
        editNamXB.setText("");
    }

    //
    class AddSach extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        String masach, tensach, tentacgia, tennxb;
        int namxb;

        public AddSach(String masach, String tensach, String tentacgia, String tennxb, int namxb) {
            this.masach = masach;
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
                postdata.put("MaSach", masach);
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
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    //call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String mMessage = response.body().string();
                    Log.e("AAA", mMessage);
                }
            });
            return null;
        }
    }

    //
    class postSach extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        String masach, tensach, tentacgia, tennxb;
        int namxb;

        public postSach(String masach, String tensach, String tentacgia, String tennxb, int namxb) {
            this.masach = masach;
            this.tensach = tensach;
            this.tentacgia = tentacgia;
            this.tennxb = tennxb;
            this.namxb = namxb;
        }


        @Override
        protected String doInBackground(String... strings) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("MaSach", masach)
                    .addFormDataPart("TenSach", tensach)
                    .addFormDataPart("TenTacGia", tentacgia)
                    .addFormDataPart("TenNXB", tennxb)
                    .addFormDataPart("NamXB", String.valueOf((int) (namxb)))
                    .setType(MultipartBody.FORM)
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
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

        @Override
        protected void onPostExecute(String s) {
            Log.d("AAA", s);
            super.onPostExecute(s);
        }
    }
}
