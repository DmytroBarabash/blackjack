package game.blackjack.common;

import game.blackjack.server.GameState;

import java.util.ArrayList;
import java.util.List;

import static game.blackjack.server.GameState.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro Barabash
 * 2013-05-21 01:30
 */
public class Protocol {

    public static final String ERR_PREFIX = "error:";
    public static final String OK_PREFIX = "OK!";

    private List<Card> deck = Card.shuffleDeck();
    private GameState state = START;
    private int bet;
    private List<Card> hand = new ArrayList<Card>();
    private List<Card> dealerHand = new ArrayList<Card>();

    {
        giveCard(dealerHand);
        giveCard(dealerHand);
    }

    private Card giveCard(List<Card> hand) {
        Card card = deck.remove(deck.size() - 1);
        hand.add(card);
        return card;
    }

    private String playDealer(int total) {
        return " you won " + bet % 2 * 3 + bet;
    }

    public String processInput(String s) {
        if (s == null) {
            return "Null received";
        }
        switch (state) {
            case START: {
                try {
                    bet = Integer.parseInt(s);
                } catch (NumberFormatException ex) {
                    return ERR_PREFIX + "Bet should be an integer";
                }
                state = BET;
                giveCard(hand);
                giveCard(hand);
                return OK_PREFIX + hand;
            }
            case BET: {
                Turn turn = Turn.getTurn(s);
                switch (turn) {
                    case HIT: {
                        Card card = giveCard(hand);
                        int total = Card.calculateTotal(hand);
                        if (total == Constants.TWENTY_ONE) {
                            state = FINISH;
                            return OK_PREFIX + card + playDealer(total);
                        } else if (total > Constants.TWENTY_ONE) {
                            state = FINISH;
                            return OK_PREFIX + card + " bust ";
                        } else {
                            return OK_PREFIX + card;
                        }
                    }
                    case STAND: {
                        state = FINISH;
                        Card card = giveCard(hand);
                        int total = Card.calculateTotal(hand);
                        return OK_PREFIX + card + playDealer(total);
                    }
                    case DOUBLE: {
                        state = FINISH;
                        return ERR_PREFIX + "not implemented";
                    }
                    default: {
                        return ERR_PREFIX + "not implemented";
                    }
                }
            }
            default: {
                return ERR_PREFIX + "unknown state";
            }
        }
    }

}
