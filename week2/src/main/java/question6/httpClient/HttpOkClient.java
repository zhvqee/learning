package question6.httpClient;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

final public class HttpOkClient {

    private static OkHttpClient HttpClient = new OkHttpClient();

    public static String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = HttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Map<String, String> paramsMap) {
        FormBody.Builder builder = new FormBody.Builder();
        paramsMap.forEach(builder::add);

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        Call call = HttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String s = HttpOkClient.get("http://127.0.0.1:7777/test-service/test/get?abc=test");
        System.out.println(s);
    }


}
