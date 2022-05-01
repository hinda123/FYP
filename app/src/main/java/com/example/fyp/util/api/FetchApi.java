package com.example.fyp.util.api;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FetchApi {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> void get(String endingPoint, Class<T> mapClass, Consumer<Boolean> onLoading, Consumer<T> onResult, Consumer<String> onError) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        androidThreadExecutor(endingPoint, "GET", mapClass, onLoading, onResult, onError, executorService);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static <T> void androidThreadExecutor(String url, String method, Class<T> mapClass, Consumer<Boolean> onLoading, Consumer<T> onResult, Consumer<String> onError, ExecutorService executorService) {
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> {
            onLoading.accept(Boolean.TRUE);
            try {
                String json = fetchRequest(url, method).getData();
                handler.post(() -> onResult.accept(jsonToObject(json, mapClass)));
            } catch (Exception e) {
                handler.post(() -> onError.accept(e.getMessage()));
            }
            onLoading.accept(Boolean.FALSE);
        });
    }

    private static <T> T jsonToObject(String json, Class<T> mapClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, mapClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return mapClass.cast(new Object());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static Response fetchRequest(String endingPoint, String method) {
        final String url = "http://192.168.43.26:3000/api/v1" + endingPoint;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod(method);
            return new Response(
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).lines().collect(Collectors.joining()),
                    httpURLConnection.getResponseCode(),
                    httpURLConnection.getRequestMethod(), Response.ResponseStatus.SUCCESS);
        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());
        }

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
