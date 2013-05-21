package game.blackjack.common;

import game.blackjack.server.GameState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro Barabash
 * 2013-05-21 22:14
 */
public class Answer implements Serializable {

    private GameState state;
    private int bet;
    private List<Card> hand;
    private List<Card> dealerHand;
    private String errorMessage;

    public Answer(Game game){
        state = game.getState();
        bet = game.getBet();
        hand = new ArrayList<Card>(game.getHand());
        dealerHand = new ArrayList<Card>(game.getDealerHand());
        errorMessage = game.getErrorMessage();
    }

    @Override
    public String toString() {
        return "state=" + state + " bet=" + bet + " hand: " + hand + ((errorMessage != null) ? " " + errorMessage : "");
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
