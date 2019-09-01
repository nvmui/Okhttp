package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Activitygeturl extends AppCompatActivity {
    Button btnChetUrl;
    EditText editUrl;
    TextView txtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitygeturl);
        AnhXa();
        btnChetUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new Geturl().execute("http://192.168.1.4/apiqltv/listUser.php");
            }
        });

    }

    private void AnhXa() {
        btnChetUrl = (Button) findViewById(R.id.btnCheckUrl);
        editUrl = (EditText) findViewById(R.id.editUrl);
        txtUrl = (TextView) findViewById(R.id.textUrl);
    }

    class Geturl extends AsyncTask<String, String, String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        @Override
        protected String doInBackground(String... strings) {
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);
            Request request = builder.build();
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
            if (!s.equals("")) {
                txtUrl.setText(s);
            } else {
                Toast.makeText(Activitygeturl.this, "Đường dẫn của bạn đang bị lỗi", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }
}
