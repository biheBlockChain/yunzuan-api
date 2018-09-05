package one.bihe.yunzuan.util;

import okhttp3.*;
import one.bihe.yunzuan.core.YunZuan;

import java.io.IOException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    public static Response post(String url, String path, Headers headers, Param param) throws IOException {
        return post(url + path, headers, param);
    }


    private static Response post(String url, Headers headers, Param param) throws IOException {
        OkHttpClient client = OkhttpUtils.getClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, param.toString());
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded");
        if (headers != null) {
            builder.headers(headers);
        }
        Response response = client.newCall(builder.build()).execute();
        return response;
    }

    public static Response get(String url, Headers headers, Param param) {
        OkHttpClient client = OkhttpUtils.getClient();
        url = url + "?" + param.toString();
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        if (headers != null) {
            builder.headers(headers);
        }
        try {
            Response response = client.newCall(builder.build()).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Param extends HashMap<String, Object> {

        public Param() {
        }

        public Param(Map<? extends String, ?> m) {
            super(m);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (containsKey("key")) {
                remove("key");
            }
            for (Entry<String, Object> entry : this.entrySet()) {
                builder.append(entry.getKey()).append("=").append(urlEncode(entry.getValue())).append("&");
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            return builder.toString();
        }

        public String urlEncode(Object str) {
            if (str == null) {
                return "";
            }
            return URLEncoder.encode(str.toString());
        }
    }

}
