package edu.project.englishstoriesbot.httpclient;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class CustomHttpClient {

    public String sendRequestGetResponse(String url) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        HttpRequest request = setUpHttpRequest(url);
        HttpResponse<String> response = setUpHttpResponse(request);
        return response.body();
    }

    private HttpRequest setUpHttpRequest(String url) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();
    }
//    private HttpResponse<String> setUpHttpResponse(HttpRequest request) throws IOException, InterruptedException, ExecutionException {
//        CompletableFuture<HttpResponse<String>> future = HttpClient
//                .newBuilder()
//                .build()
//                .sendAsync(request,  HttpResponse.BodyHandlers.ofString());
//        return future.get();
//    }
    private HttpResponse<String> setUpHttpResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
