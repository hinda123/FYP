package com.example.fyp.util.api;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.fyp.model.Token;
import com.example.fyp.screans.Login;
import com.example.fyp.util.TokenConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FetchApi {
    private static final String url = "http://34.129.127.180/api/v1";
    private final TokenConfig tokenConfig;

    public FetchApi(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    public static String getUrl(String resource){
        return url.concat(resource);
    }

    public static String getImageUrl(String resource){
        if(resource == null) return getUrl("/events/media/download/file/dasdasdasd");
        return getUrl("/events/media/download/file/").concat(resource);
    }

    public <T> void get(String endingPoint, Class<T> mapClass, Consumer<Boolean> onLoading, Consumer<T> onResult, Consumer<String> onError) {
        Consumer<Handler> handlerConsumer = handler -> getDataRequest(endingPoint, mapClass, onResult, onError, handler);
        androidThreadExecutor(onLoading, handlerConsumer);
    }


    public <T> void post(String endingPoint, T t, Consumer<Boolean> onLoading, Consumer<String> onResult, Consumer<String> onError) {
        Consumer<Handler> handlerConsumer = handler -> postDataRequest(endingPoint, t, onResult, onError, handler);
        androidThreadExecutor(onLoading, handlerConsumer);
    }


    private <T> void androidThreadExecutor(Consumer<Boolean> onLoading, Consumer<Handler> handlerConsumer) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> {
            onLoading.accept(Boolean.TRUE);
            handlerConsumer.accept(handler);
            onLoading.accept(Boolean.FALSE);
        });
    }

    private <T> void postDataRequest(String endingPoint, T t, Consumer<String> onResult, Consumer<String> onError, Handler handler) {
        try {
            String json = postRequest(endingPoint, objectToJson(t), new HashMap<>());
            handler.post(() -> onResult.accept(json));
        } catch (Exception e) {
            handler.post(() -> onError.accept(e.getMessage()));
        }
    }


    private <T> void getDataRequest(String url, Class<T> mapClass, Consumer<T> onResult, Consumer<String> onError, Handler handler) {
        try {
            String json = getRequest(url).getData();
            handler.post(() -> onResult.accept(jsonToObject(json, mapClass)));
        } catch (Exception e) {
            handler.post(() -> onError.accept(e.getMessage()));
        }
    }

    public static <T> T jsonToObject(String json, Class<T> mapClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, mapClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private <T> String objectToJson(Object o) {
        if (o == null)
            return "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String postRequest(String endingPoint, String payload, Map<String, String> headers) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection("POST", url + endingPoint);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            headers.forEach(httpURLConnection::setRequestProperty);
            sendRequest(payload, httpURLConnection);
            return new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private void sendRequest(String payload, HttpURLConnection httpURLConnection) {
        try (OutputStream os = httpURLConnection.getOutputStream(); OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");) {
            osw.write(payload);
            httpURLConnection.connect();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Response getRequest(String endingPoint) {
        HttpURLConnection httpURLConnection = getHttpURLConnection("GET", url + endingPoint);
        try {
            return new Response(
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).lines().collect(Collectors.joining()),
                    httpURLConnection.getResponseCode(),
                    httpURLConnection.getRequestMethod(), Response.ResponseStatus.SUCCESS);
        } catch (IOException e) {
            try {
                checkIfItHasAuth(httpURLConnection);
                checkIfItHasExpiredAuth(httpURLConnection);
            }catch (IOException exception){
            }
            throw new RuntimeException(e.getMessage());
        }

    }

    private void checkIfItHasAuth(HttpURLConnection httpURLConnection) throws IOException {
        if (httpURLConnection.getResponseCode() != 401) return;
        tokenConfig.removeToken();
        Intent intent = new Intent(tokenConfig.getContext(), Login.class);
        tokenConfig.getContext().startActivity(intent);
    }

    private void checkIfItHasExpiredAuth(HttpURLConnection httpURLConnection)  {
        try {
            if (httpURLConnection.getResponseCode() != 403) return;
            Token token = tokenConfig.getToken();
            String jwt = getRequest("/auth/referesh-token?refreshToken="+token.getRefreshToken()).getData();
            tokenConfig.saveToken(jwt);
        }catch (Exception e){
            e.printStackTrace();;
        }
    }


    @NonNull
    private HttpURLConnection getHttpURLConnection(String method, String url){
        Token token = tokenConfig.getToken();
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setRequestProperty("Authorization", token.getAccessToken());
        } catch (Exception e) {
          e.printStackTrace();
        }
        return httpURLConnection;
    }

    private static class Response {
        private final String response;
        private final int statusCode;
        private final String method;

        enum ResponseStatus {SUCCESS, FAILED}

        private final ResponseStatus status;

        public Response(String response, int statusCode, String method, ResponseStatus status) {
            this.response = response;
            this.statusCode = statusCode;
            this.method = method;
            this.status = status;
        }

        public String getData() {
            return response;
        }

        public ResponseStatus getStatus() {
            return status;
        }

        public String getMethod() {
            return method;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

}
