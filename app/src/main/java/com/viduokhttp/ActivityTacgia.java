package com.viduokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class ActivityTacgia extends AppCompatActivity {
//    ArrayList<TacGia> arrayTacGia;
//    TacGiaAdapter tacGiaAdapter;
    ImageView img_AddTG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacgia);
        img_AddTG = (ImageView) findViewById(R.id.img_AddTG);
        img_AddTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityTacgia.this, ActivityAddtacgia.class);
                startActivity(intent);
            }
        });
        getData();
    }

    private void getData() {
        final ListView listTacGia = (ListView) findViewById(R.id.listTacGia);
        //khai báo thư viện client
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();
        Type tacGiaType = Types.newParameterizedType(List.class, TacGia.class);
        final JsonAdapter<List<TacGia>> jsonAdapter = moshi.adapter(tacGiaType);

        Request request = new Request.Builder()
                .url(new config().urlphp+"listTacGiaAll.php")
                .method("GET", null)
                .build();
        //Response response = okHttpClient.newCall(request).execute();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error aa", "Network Error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Lấy thông tin JSON trả về. Bạn có thể log lại biến json này để xem nó như thế nào.
                String json = response.body().string();
                final List<TacGia> tacGias = jsonAdapter.fromJson(json);
                // Cho hiển thị lên ListView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listTacGia.setAdapter(new TacGiaAdapter(ActivityTacgia.this, R.layout.list_item_tacgia, tacGias));
//                        listTacGia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(ActivityTacgia.this, "" + i, Toast.LENGTH_LONG).show();
//                            }
//                        });
                    }
                });
            }
        });
    }

}
