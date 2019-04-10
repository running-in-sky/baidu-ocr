package cn.zzzhy.service;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class OkHttpService {

    private static OkHttpClient client = new OkHttpClient();

    static String sendPostForm(String url, final Map<String, String> params) throws IOException {
        FormBody.Builder builder = new FormBody.Builder(StandardCharsets.UTF_8);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded").url(url).post(requestBody).build();
        return client.newCall(request).execute().body().string();
    }

    static String sendGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute().body().string();
    }

    public static void main(String[] args) throws Exception {
        String url = "https://httpbin.org/post";
        Map<String, String> params = new HashMap<>();
        params.put("foo", "bar中文");
        String rsp = sendPostForm(url, params);
        System.out.println("http post rsp:" + rsp);

        url = "https://httpbin.org/get";
        System.out.println("http get rsp:" + sendGet(url));
    }
}