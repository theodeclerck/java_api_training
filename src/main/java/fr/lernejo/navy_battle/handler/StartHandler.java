package fr.lernejo.navy_battle.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.Server.StartGame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class StartHandler implements HttpHandler {

    private final StringBuilder url = new StringBuilder();

    public StartHandler(int port) { this.url.append("http://localhost:").append(port); }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) { NotFoundMethod(exchange); }
        else {
            JsonStartHandler jsonRequest = ParseBody(exchange);
            if (jsonRequest == null || jsonRequest.message.equals("\"\"") || jsonRequest.id.equals("\"\"") || jsonRequest.url.equals("\"\"")) { SendResponse(exchange, "Bad Json", 400); }
            else { SendResponse(exchange, "{\n\t\"id\":\"" + UUID.randomUUID() + "\",\n\t\"url\":\"" + this.url + "\",\n\t\"message\":\"May the best code win\"\n}", 202); }
            var Game = new StartGame(jsonRequest, jsonRequest);
            try { Game.LaunchGame();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void SendResponse(HttpExchange exchange, String s, int rcode) throws IOException {
        exchange.sendResponseHeaders(rcode, s.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(s.getBytes());
        }
    }

    private void NotFoundMethod(HttpExchange exchange) throws IOException {
        String error = "TNull";
        exchange.sendResponseHeaders(404, error.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(error.getBytes());
        }
    }

    private String StreamToString(InputStream s) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = s.read()) > 0) {
            sb.append((char) c);
        }
        return sb.toString();
    }

    private JsonStartHandler ParseBody(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonStartHandler jsonRequest;
        String sb = StreamToString(exchange.getRequestBody());
        if (sb.isBlank()) { return null; }
        else {
            try {
                jsonRequest = mapper.readValue(sb, JsonStartHandler.class);
            } catch (IllegalArgumentException e) {
                exchange.sendResponseHeaders(400, "Bad request".length());
                throw new IllegalArgumentException();
            }
        }
        return jsonRequest;
    }
}
