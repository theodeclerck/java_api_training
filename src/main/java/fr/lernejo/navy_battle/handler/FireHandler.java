package fr.lernejo.navy_battle.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.game.Cell;
import fr.lernejo.navy_battle.game.SunkBoat;

import java.io.IOException;
import java.io.OutputStream;

public class FireHandler implements HttpHandler {
    final Cell map;

    public FireHandler() { this.map = new Cell(); }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) NoResult(exchange);

        var display_Json = Parser(exchange);
        if (display_Json != null) {
            int boatStatus = CheckBoat(display_Json);
            if (boatStatus == 0) {
                SendResponse(exchange, "{\n\t\"consequence\": \"miss\",\n\t\"shipLeft\": true\n}", 200);
            } else if (boatStatus == 2) {
                SendResponse(exchange, "{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", 200);
            } else { ShipLeft(exchange); }
        }
    }

    private void ShipLeft(HttpExchange exchange) throws IOException {
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (map.cells[i][j] != 0) {
                    SendResponse(exchange, "{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": true\n}", 200);
                    return;
                }
            }
        }
        SendResponse(exchange, "{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": false\n}", 200);
    }

    private int CheckBoat(JsonFireHandler display_Json) {
        if (map.cells[display_Json.row][display_Json.col] == 0) {
            return 0;
        }
        else {
            var sb = new SunkBoat();
            int statusBoat = sb.sunkBoat(map, display_Json);
            map.cells[display_Json.row][display_Json.col] = 0;
            if (statusBoat == 0)
                return 1;
            else
                return 2;
        }
    }

    private void SendResponse(HttpExchange exchange, String s, int rcode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(rcode, s.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(s.getBytes());
        }
    }

    private JsonFireHandler Parser(HttpExchange exchange) throws IOException {
        String param = exchange.getRequestURI().getQuery();
        final int length = param.substring(param.indexOf("=") + 1).length();
        if (length < 2) {
            SendResponse(exchange, "Wrong Param", 404);
            return null;
        }
        final int number = param.substring(param.indexOf("=") + 1).charAt(1) - '0';
        if (length > 3 || (length == 3 && !param.substring(param.indexOf("=") + 2).equals("10")) || param.substring(param.indexOf("=") + 1).charAt(0) > 'J' || number == 0 || number > 10) {
            SendResponse(exchange, "Wrong Param", 404);
            return null;
        }
        else
            return new JsonFireHandler(param.substring(param.indexOf("=") + 1));
    }

    private void NoResult(HttpExchange exchange) throws IOException {
        String error = "No Result";
        exchange.sendResponseHeaders(404, error.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(error.getBytes());
        }
    }
}
