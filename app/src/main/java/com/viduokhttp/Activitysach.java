package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Activitysach extends AppCompatActivity {
    ImageView im_edit, im_delete, im_Add;
    TextView txtTenSach;
    ArrayList<Sach> arraySach;
    SachAdapter sachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitysach);
        im_edit = (ImageView) findViewById(R.id.im_edit);
        im_delete = (ImageView) findViewById(R.id.im_delete);
        im_Add=(ImageView)findViewById(R.id.im_add);
        im_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Activitysach.this, ActivityAddsach.class);
                startActivity(intent);
            }
        });
        getData();
    }

    public void getData() {
        final RecyclerView rvSach = (RecyclerView) findViewById(R.id.rv_sach);
        rvSach.setLayoutManager(new LinearLayoutManager(this));
        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();
        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type sachsType = Types.newParameterizedType(List.class, Sach.class);
        final JsonAdapter<List<Sach>> jsonAdapter = moshi.adapter(sachsType);
        // Tạo request lên server.
        Request request = new Request.Builder()
                .url("http://192.168.26.138:1337/saches/")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZDYzNDk0MjA2NjhkYTBlYTBhNWE2ZjMiLCJpZCI6IjVkNjM0OTQyMDY2OGRhMGVhMGE1YTZmMyIsImlhdCI6MTU2NzEzNTM5NSwiZXhwIjoxNTY5NzI3Mzk1fQ.E3_GLZsQQJ5kczZw-SzdWcf008ZPeQDbb1_Z-gKXfXs")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZDQ1NTk3MjhiZTA1MzJhZDg3NDFjODMiLCJpZCI6IjVkNDU1OTcyOGJlMDUzMmFkODc0MWM4MyIsImlhdCI6MTU2NzE0NDAzMSwiZXhwIjoxNTY5NzM2MDMxfQ.WcJZ-tnYoPBivI9oCS682MfXAgSSXKFIsEyIh_nCckU")
                .build();
        // Thực thi request.
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error aa", "Network Error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Lấy thông tin JSON trả về. Bạn có thể log lại biến json này để xem nó như thế nào.
                String json = response.body().string();
                final List<Sach> sach = jsonAdapter.fromJson(json);
                // Cho hiển thị lên RecyclerView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvSach.setAdapter(new SachAdapter(sach, Activitysach.this));
                    }
                });
            }
        });
    }
}
