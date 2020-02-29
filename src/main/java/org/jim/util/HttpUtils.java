package org.jim.util;

import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp Utils
 *
 * @author JSJ
 */
public class HttpUtils {
    public static final long SLOW_RESPONSE = 500L;
    public static final long READ_TIMEOUT_SEC = 60L;
    public static final long CONNECT_TIMEOUT_SEC = 60L;

    public static final int OK_CODE = 200;
    public static final int ERR_CODE = 500;

    public static final String APPLICATION_JSON = "application/json";
    public static final String AUTHORIZATION = "Authorization";

    private static Map<String, Long> SLOW_URLS;
    static {
        SLOW_URLS = new HashMap<>(16);
    }

    /**
     * 创建一个新的OkHttpClient实例
     *
     * @return
     */
    public static OkHttpClient createHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 创建一个Base Authentication的OkHttpClient实例
     *
     * @return
     */
    public static OkHttpClient createBaseAuthHttpClient(String user, String password) {
        final String credential = Credentials.basic(user, password);
        return new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS)
                .authenticator((a, b) ->
                        b.request().newBuilder().header(AUTHORIZATION, credential).build())
                .build();
    }

    /**
     * GET 请求
     *
     * @param httpClient
     * @param url
     * @param param
     * @return
     * @throws NetworkException
     */
    public static String doGet(OkHttpClient httpClient, String url, String param)
            throws NetworkException {
        long st = System.currentTimeMillis();

        HttpUrl httpUrl = HttpUrl.parse(url + param);
        if (null == httpUrl) {
            throw new NetworkException(ERR_CODE, url + param);
        }

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        try {
            return sendRequest(httpClient, request);
        } finally {
            long ct = System.currentTimeMillis() - st;
            if (SLOW_RESPONSE < ct) {
                Long count = SLOW_URLS.get(url);
                SLOW_URLS.put(url, null == count ? 1L : ++count);
            }
        }
    }

    /**
     * POST 请求
     *
     * @param httpClient
     * @param url
     * @param json
     * @return
     * @throws NetworkException
     */
    public static String doPost(OkHttpClient httpClient, String url, String json)
            throws NetworkException {
        long st = System.currentTimeMillis();

        HttpUrl httpUrl = HttpUrl.parse(url);
        if (null == httpUrl) {
            throw new NetworkException(ERR_CODE, url);
        }

        RequestBody body = RequestBody.create(MediaType.parse(APPLICATION_JSON), json);
        Request request = new Request.Builder()
                .url(httpUrl)
                .post(body)
                .build();

        try {
            return sendRequest(httpClient, request);
        } finally {
            long ct = System.currentTimeMillis() - st;
            if (SLOW_RESPONSE < ct) {
                Long count = SLOW_URLS.get(url);
                SLOW_URLS.put(url, null == count ? 1L : ++count);
            }
        }
    }

    /**
     * 200时返回响应，其他情况返回NetworkException
     *
     * @param httpClient
     * @param request
     * @return
     * @throws NetworkException
     */
    private static String sendRequest(OkHttpClient httpClient, Request request)
            throws NetworkException {
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();

            // NPE
            if (null == response || null == response.body()) {
                throw new NetworkException(ERR_CODE, "No response");
            }

            int code = response.code();
            String body = response.body().string();

            // 异常状态码
            if (OK_CODE != code) {
                throw new NetworkException(code, body);
            }

            return body;
        } catch (IOException e) {
            throw new NetworkException(ERR_CODE, e.getMessage());
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }

    /**
     * 返回慢查询请求列表
     *
     * @param count
     * @return
     */
    public static List<String> listSlowUrls(long count) {
        List<String> urlList = new ArrayList<>(16);
        SLOW_URLS.forEach((k, v) -> {
            if (v >= count) {
                urlList.add(k);
            }
        });
        return urlList;
    }

    /**
     * 重置慢查询
     */
    public static void resetSlowUrls() {
        SLOW_URLS.clear();
    }

}
