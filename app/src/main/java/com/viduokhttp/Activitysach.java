package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Activitysach extends AppCompatActivity {
    ImageView im_edit, im_delete, im_Add, im_Exit;
    //    EditText editSach;
    TextView txtTenSach;
    ArrayList<Sach> arraySach;
    SachAdapter sachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitysach);
        im_Exit=(ImageView)findViewById(R.id.im_Exit);
        im_edit = (ImageView) findViewById(R.id.im_edit);
        im_delete = (ImageView) findViewById(R.id.im_delete);
        im_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        im_Add = (ImageView) findViewById(R.id.im_add);
        im_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activitysach.this, ActivityAddsach.class);
                startActivity(intent);
                finish();
            }
        });
        getData();
    }

    public void getData() {
        final ListView listSach = (ListView) findViewById(R.id.listSach);
        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();
        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type sachsType = Types.newParameterizedType(List.class, Sach.class);
        final JsonAdapter<List<Sach>> jsonAdapter = moshi.adapter(sachsType);
        // Tạo request lên server.
        Request request = new Request.Builder()
//                .url("http://192.168.26.111/apiqltv/listSachAll.php")
//                .url("http://192.168.57.191/apiqltv/listSach.php")
                .url("http://192.168.1.2:1337/Saches")
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
                final String json = response.body().string();
                final List<Sach> sach = jsonAdapter.fromJson(json);
                // Cho hiển thị lên RecyclerView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listSach.setAdapter(new AdapterSach(Activitysach.this, R.layout.list_item_sach, sach));
                    }
                });
            }
        });
    }
}
