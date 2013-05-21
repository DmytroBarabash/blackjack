package game.blackjack.server;

import game.blackjack.common.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro Barabash
 * 2013-05-21 01:41
 */
public class Processor implements Runnable {

    private final Socket socket;

    public Processor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client connected");

            String inputLine, outputLine;
            Protocol protocol = new Protocol();
            out.println("hello");

            while ((inputLine = in.readLine()) != null) {
                outputLine = protocol.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equalsIgnoreCase("by"))
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    System.out.println("Input stream couldn't be closed");
                }
            }
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Socket couldn't be closed");
            }
        }
    }

}
