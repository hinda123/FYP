package com.example.fyp.util.api;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FetchApi {
    private static final String url = "http://192.168.43.26:3000/api/v1";


    public static <T> void get(String endingPoint, Class<T> mapClass, Consumer<Boolean> onLoading, Consumer<T> onResult, Consumer<String> onError) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Consumer<Handler> handlerConsumer = handler -> getDataRequest(endingPoint, mapClass, onResult, onError, handler);
        androidThreadExecutor(onLoading, handlerConsumer, executorService);
    }


    public static <T> void post(String endingPoint, T t, Consumer<Boolean> onLoading, Consumer<String> onResult, Consumer<String> onError) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Consumer<Handler> handlerConsumer = handler -> {
            try {
                String json = postRequest(endingPoint, objectToJson(t));
                handler.post(() -> onResult.accept(json));
            } catch (Exception e) {
                handler.post(() -> onError.accept(e.getMessage()));
            }
        };
        androidThreadExecutor(onLoading, handlerConsumer, executorService);
    }


    private static <T> void androidThreadExecutor(Consumer<Boolean> onLoading, Consumer<Handler> handlerConsumer, ExecutorService executorService) {
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> {
            onLoading.accept(Boolean.TRUE);
            handlerConsumer.accept(handler);
            onLoading.accept(Boolean.FALSE);
        });
    }


    private static <T> void getDataRequest(String url, Class<T> mapClass, Consumer<T> onResult, Consumer<String> onError, Handler handler) {
        try {
            String json = getRequest(url).getData();
            handler.post(() -> onResult.accept(jsonToObject(json, mapClass)));
        } catch (Exception e) {
            handler.post(() -> onError.accept(e.getMessage()));
        }
    }

    private static <T> T jsonToObject(String json, Class<T> mapClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, mapClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static <T> String objectToJson(Object o) {
        if(o == null)
            return "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String postRequest(String endingPoint, String payload) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection("POST", url + endingPoint);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            sendRequest(payload, httpURLConnection);
            return new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).lines().collect(Collectors.joining());
        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());
        }

    }

    private static void sendRequest(String payload, HttpURLConnection httpURLConnection) {
        try (OutputStream os = httpURLConnection.getOutputStream(); OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");) {
            osw.write(payload);
            httpURLConnection.connect();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Response getRequest(String endingPoint) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection("GET", url + endingPoint);
            return new Response(
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).lines().collect(Collectors.joining()),
                    httpURLConnection.getResponseCode(),
                    httpURLConnection.getRequestMethod(), Response.ResponseStatus.SUCCESS);
        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());
        }

    }

    @NonNull
    private static HttpURLConnection getHttpURLConnection(String method, String url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50VHlwZSI6IkFETUlOIiwiaWQiOiI0MDE1MGMxMC0yYWZlLTQ3MWItYjRiOC1iY2JkYmZlYTc0NjYiLCJpYXQiOjE2NTE0OTY4NDUsImV4cCI6MTY1MTUwMDQ0NX0.WY9gCOwybUURlm_kJMLkK7Rh6B4o3efghUecwOjceDA");

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
