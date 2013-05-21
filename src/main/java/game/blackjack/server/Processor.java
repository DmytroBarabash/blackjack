package game.blackjack.server;

import game.blackjack.common.Answer;
import game.blackjack.common.Game;

import java.io.*;
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
        ObjectOutputStream out = null;
        BufferedReader in = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client connected");

            String inputLine;
            Game game = new Game();
            Answer answer = new Answer(game);
            out.writeObject(answer);
            out.flush();

            while ((inputLine = in.readLine()) != null) {
                answer = game.processInput(inputLine);
                out.writeObject(answer);
                out.flush();
                System.out.println(answer);
                if (game.getState() == GameState.FINISH) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("Output stream couldn't be closed");
                }
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
