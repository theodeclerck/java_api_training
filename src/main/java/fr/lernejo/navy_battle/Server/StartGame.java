package fr.lernejo.navy_battle.Server;

import fr.lernejo.navy_battle.handler.JsonStartHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StartGame {
    public final JsonStartHandler AdversaryJson;
    public final JsonStartHandler MyJson;

    public StartGame(JsonStartHandler adJson, JsonStartHandler myJson) {
        this.AdversaryJson = adJson;
        this.MyJson = myJson;
    }

    public void LaunchGame() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(AdversaryJson.url.substring(1, AdversaryJson.url.length() - 1) + "/api/game/fire?cell=A1"))
            .setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
