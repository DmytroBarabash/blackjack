package game.blackjack.client;

import game.blackjack.common.Answer;
import game.blackjack.common.Constants;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = Constants.DEFAULT_HOST;
        int port = Constants.DEFAULT_PORT;
        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                System.out.println("Bad port number " + args[1]);
                System.exit(-1);
            }
        }

        Socket socket = new Socket(host, port);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String fromUser;
        Answer answer;
        while ((answer = (Answer) in.readObject()) != null) {
            if (answer.getErrorMessage() != null) {
                System.out.println("Warning! " + answer.getErrorMessage());
            }
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
                    System.out.println("Your hand: " + answer.getHand());
                    System.out.println("Dealer's hand: " + answer.getDealerHand());
                    int bet = answer.getBet();
                    if (bet > 0) {
                        System.out.println("Your bet is " + bet);
                    }
                    switch (answer.getResult()) {
                        case DRAW: {
                            System.out.println("Draw");
                            break;
                        }
                        case WON:
                        case LOSE: {
                            System.out.println("You " + answer.getResult().toString().toLowerCase());
                            break;
                        }
                        case UNDEFINED: {
                            System.out.println("Something is wrong");
                        }
                    }
                    out.close();
                    in.close();
                    return;
                }
            }
        }

    }

}
