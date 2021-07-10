package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.Server.InitServer;
import fr.lernejo.navy_battle.Server.StartServer;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = Integer.parseInt(args[0]);
        var server = new StartServer(port);
        if (args.length > 1)
            new InitServer().New(port, args[1]);
    }
}
