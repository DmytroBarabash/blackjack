package game.blackjack.server;

import game.blackjack.common.Constants;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) throws IOException {
        int port = Constants.DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.out.println("Bad port number " + args[0]);
                System.exit(-1);
            }
        }

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening port " + port + "\nUse Ctrl+C to exit");
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(-1);
        }
        try {
            while (true) {
                new Thread(new Processor(serverSocket.accept())).start();
            }
        } finally {
            serverSocket.close();
        }
    }

}
