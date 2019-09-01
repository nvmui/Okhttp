package com.viduokhttp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SachHandler extends AsyncTask<String, String, String> {
    OkHttpClient client = new OkHttpClient();
    Context c;

    @Override
    protected String doInBackground(String... params) {

        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            Toast.makeText(c, "Connect failed", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
