package game.blackjack.client;

import game.blackjack.common.Answer;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 9090);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String fromUser;
        Answer answer;
        while ((answer = (Answer) in.readObject()) != null) {
            System.out.println("Server: " + answer);
            switch (answer.getState()) {
                case START: {
                    System.out.println("Enter your bet:");
                    fromUser = stdIn.readLine();
                    if (fromUser != null) {
                        out.println(fromUser);
                    }
                    break;
                }
                case BET: {
                    System.out.println("Your hand: " + answer.getHand());
                    System.out.println("Choose hit, stand or double (h, s, d respectively):");
                    fromUser = stdIn.readLine();
                    if (fromUser != null) {
                        out.println(fromUser);
                    }
                    break;
                }
                case FINISH: {
                    System.out.println("Fin");
                    System.out.println("Your hand: " + answer.getHand());
                    System.out.println("Dealer's hand: " + answer.getDealerHand());
                }
            }
        }
    }

}
