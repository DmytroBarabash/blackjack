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
public class Game {

    private List<Card> deck = Card.shuffleDeck();

    private GameState state;
    private int bet;
    private List<Card> hand;
    private List<Card> dealerHand;
    private String errorMessage;

    public Game() {
        state = START;
        hand = new ArrayList<Card>();
        dealerHand = new ArrayList<Card>();
        giveCard(dealerHand);
        giveCard(dealerHand);
    }

    private Card giveCard(List<Card> hand) {
        Card card = deck.remove(deck.size() - 1);
        hand.add(card);
        return card;
    }

    private String playDealer(int total) {
        int dealerTotal;
        while ((dealerTotal = Card.calculateTotal(dealerHand)) < 17) {
            giveCard(dealerHand);
        }
        if (dealerTotal > Constants.TWENTY_ONE) {
            return " you won " + bet * 2;
        } else if (dealerTotal == Constants.TWENTY_ONE && total == Constants.TWENTY_ONE) {
            return " no winner";
        } else if (total > dealerTotal) {
            return " you won " + bet * 2;
        } else {
            return " dealer won";
        }
    }

    public Answer processInput(String s) {
        if (s == null || s.isEmpty()) {
            errorMessage = "Empty request";
            return new Answer(this);
        }
        switch (state) {
            case START: {
                try {
                    bet = Integer.parseInt(s);
                } catch (NumberFormatException ex) {
                    errorMessage = "Bet should be an integer";
                    return new Answer(this);
                }
                state = BET;
                giveCard(hand);
                giveCard(hand);
                break;
            }
            case BET: {
                Turn turn = Turn.getTurn(s);
                switch (turn) {
                    case HIT: {
                        giveCard(hand);
                        int total = Card.calculateTotal(hand);
                        if (total == Constants.TWENTY_ONE) {
                            state = FINISH;
                            System.out.println(playDealer(total));
                        } else if (total > Constants.TWENTY_ONE) {
                            state = FINISH;
                        }
                        break;
                    }
                    case STAND: {
                        state = FINISH;
                        int total = Card.calculateTotal(hand);
                        System.out.println(playDealer(total));
                        break;
                    }
                    case DOUBLE: {
                        state = FINISH;
                        errorMessage = "not implemented";
                        break;
                    }
                    default: {
                        errorMessage = "unknown command";
                        break;
                    }
                }
                break;
            }
            case FINISH: {
                break;
            }
            default: {
                errorMessage = "unknown state";
            }
        }
        return new Answer(this);
    }

    public GameState getState() {
        return state;
    }

    public int getBet() {
        return bet;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getDealerHand() {
        return dealerHand;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
