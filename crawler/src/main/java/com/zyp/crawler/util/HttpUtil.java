package com.zyp.crawler.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    private static final MediaType MEDIA_TYPE_HTML = MediaType.parse("text/html");

    private static OkHttpClient client = new OkHttpClient();


    public static String getForObject(String uri) throws Exception {
        return exchange("GET", uri, null);
    }


    public static String postForObject(String uri, Object object) throws Exception {
        return exchange("POST", uri, object);
    }


    public static String putForObject(String uri, Object object) throws Exception {
        return exchange("PUT", uri, object);
    }


    public static String patchForObject(String uri, Object object) throws Exception {
        return exchange("PATCH", uri, object);
    }


    public static String deleteForObject(String uri) throws Exception {
        return exchange("DELETE", uri, null);
    }


    private static String exchange(String method, String url, Object object) throws Exception {
        RequestBody requestBody = null;
        if (object != null) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, JsonUtil.dumps(object));
        }

        Response response;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .method(method, requestBody)
                    .build();

            response = client.newCall(request).execute();
        } catch (IOException e) {
            LOGGER.error("http connect error  method:{}  url:{}  body:{}", method, url, JsonUtil.dumps(object));
            throw e;
        }

        if(!response.isSuccessful()){
            LOGGER.error("http response error  mehtod:{}  url:{}  body:{}", method, url, JsonUtil.dumps(object));
            throw new Exception("http response error");
        }

        String responseBody;
        Integer responseCode;
        try {
            responseCode = response.code();
            responseBody = response.body().string();
        } catch (Exception e) {
            LOGGER.error("http read response error  mehtod:{}  url:{}  body:{}", method, url, JsonUtil.dumps(object));
            throw new Exception("http read response error");
        } finally {
            response.close();
        }

        LOGGER.debug("http request success  method:{}  url:{}  body:{}  responseCode:{}  responseBody:{}", method, url, JsonUtil.dumps(object), responseCode, responseBody);

        return responseBody;
    }


}
