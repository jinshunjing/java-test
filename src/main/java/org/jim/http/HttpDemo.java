package org.jim.http;

import okhttp3.OkHttpClient;
import org.jim.util.HttpUtils;
import org.jim.util.NetworkException;

import java.util.List;

public class HttpDemo {

    public void demoBtc() {
        OkHttpClient client = HttpUtils.createHttpClient();

        String url = "http://121.196.197.246:4001/insight-api/blocks/";
        String param = "?limit=1";
        int k = 5;
        for (int i = 0; i < k; i++) {
            try {
                String body = HttpUtils.doGet(client, url, param);
                System.out.println(body);
            } catch (NetworkException e) {
                e.printStackTrace();
            }
        }

        List<String> urlList = HttpUtils.listSlowUrls(1L);
        urlList.forEach(System.out::println);
    }
}
