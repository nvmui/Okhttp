package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
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

public class ActivityNhanvien extends AppCompatActivity {
    ImageView im_edit, im_delete;
    ArrayList<NhanVien> arrayNhanvien;
    NhanVienAdapter nhanVienAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanvien);
        getData();
    }

    public void getData() {
        final ListView lvNhanvien = (ListView) findViewById(R.id.lvNhanVien);
        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();
        // Khởi tạo Moshi adapter để biến đổi json sang model java
        Moshi moshi = new Moshi.Builder().build();
        Type nhanvienType = Types.newParameterizedType(List.class, NhanVien.class);
        final JsonAdapter<List<NhanVien>> jsonAdapter = moshi.adapter(nhanvienType);
        // Tạo request lên server.
        Request request = new Request.Builder()
                .url("http://192.168.26.138/apiqltv/listnhanvien.php")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
        //      .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZDYzNDk0MjA2NjhkYTBlYTBhNWE2ZjMiLCJpZCI6IjVkNjM0OTQyMDY2OGRhMGVhMGE1YTZmMyIsImlhdCI6MTU2NzEzNTM5NSwiZXhwIjoxNTY5NzI3Mzk1fQ.E3_GLZsQQJ5kczZw-SzdWcf008ZPeQDbb1_Z-gKXfXs")
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
                final List<NhanVien> nhanViens = jsonAdapter.fromJson(json);
                // Cho hiển thị lên ListView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvNhanvien.setAdapter(new NhanVienAdapter(ActivityNhanvien.this, R.layout.item_nhanvien, nhanViens));
                    }
                });
            }
        });
    }
}
