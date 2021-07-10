package fr.lernejo.navy_battle.Server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.handler.FireHandler;
import fr.lernejo.navy_battle.handler.PingHandler;
import fr.lernejo.navy_battle.handler.StartHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class StartServer {
    final HttpServer server;

    public StartServer(int port) throws IOException {
        InetSocketAddress soc = new InetSocketAddress(port);
        this.server = HttpServer.create(soc, 0);

        this.server.setExecutor(Executors.newSingleThreadExecutor());

        this.server.createContext("/ping", new PingHandler());
        this.server.createContext("/api/game/start", new StartHandler(port));
        this.server.createContext("/api/game/fire" , new FireHandler());
        this.server.start();
    }

    public final void Stop() {
        this.server.stop(0);
    }
}
