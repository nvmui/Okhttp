package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final String URL = "http://i2.kym-cdn.com/photos/images/newsfeed/000/101/781/Y0UJC.png";
    Button downloadBtn, btnGetUrl, btnListSach, btnNhanvien;
    ImageView mImage;
    ListView lvUser;
    ArrayList<User> arrayUser;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnListSach=(Button) findViewById(R.id.btnSach);
        btnListSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Activitysach.class);
                startActivity(intent);
            }
        });
        btnGetUrl = (Button) findViewById(R.id.btnGeturl);
        btnGetUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Activitygeturl.class);
                startActivity(intent);
            }
        });
        downloadBtn = (Button) findViewById(R.id.button);
        mImage = (ImageView) findViewById(R.id.imageView);
        downloadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                downloadBtn.setVisibility(View.INVISIBLE);
                OkHttpHandler handler = new OkHttpHandler();
                byte[] image;

                try {
                    image = handler.execute(URL).get();

                    if (image != null && image.length > 0) {

                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,
                                image.length);
                        mImage.setImageBitmap(bitmap);
                        mImage.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Connect Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getData();
        btnNhanvien=(Button)findViewById(R.id.btnNhanVien);
        btnNhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ActivityNhanvien.class);
                startActivity(intent);
            }
        });

    }
    public void getData(){
        final RecyclerView rvUsers = (RecyclerView) findViewById(R.id.rv_users);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();
        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type usersType = Types.newParameterizedType(List.class, User.class);
        final JsonAdapter<List<User>> jsonAdapter = moshi.adapter(usersType);
        // Tạo request lên server.
        Request request = new Request.Builder()
                .url("http://192.168.26.138/apiqltv/listUser.php")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZDQ1NTk3MjhiZTA1MzJhZDg3NDFjODMiLCJpZCI6IjVkNDU1OTcyOGJlMDUzMmFkODc0MWM4MyIsImlhdCI6MTU2NTUyOTg4MywiZXhwIjoxNTY4MTIxODgzfQ.cW4UN8vF4Fpn4kmiGwdCbjXWasSKzt_lMlCXmVTq8Yw")
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
                final List<User> users = jsonAdapter.fromJson(json);
                // Cho hiển thị lên RecyclerView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvUsers.setAdapter(new UserAdapter(users, MainActivity.this));
                    }
                });
            }
        });
    }

}
