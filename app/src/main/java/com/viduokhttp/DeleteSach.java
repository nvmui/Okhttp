package com.viduokhttp;

import android.os.AsyncTask;
import android.util.Log;

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

public class DeleteSach extends AsyncTask<String, Void, String> {
    OkHttpClient client = new OkHttpClient.Builder()
            .build();
    public String id;

    public DeleteSach(String id) {
        this.id = id;
    }

    @Override
    protected String doInBackground(String... strings) {
        MediaType mediaType = MediaType.parse("text/plain");
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("id", id);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(mediaType, postdata.toString());
        Request request = new Request.Builder()
//                .url("http://192.168.1.2:1337/saches/" + id)
                .url(strings[0])
                .method("DELETE", body)
                .addHeader("Authorization", new config().token)
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
