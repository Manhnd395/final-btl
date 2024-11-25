package lma.librarymanagementapplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleBookAPI {
    private static final String API_KEY = "AIzaSyBYUWQCspyIefUcj7lnSr6_Yy_VQBNGRt0";

    public static String searchBook(String query) throws Exception {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
