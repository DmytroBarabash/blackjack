package game.blackjack.common;

import game.blackjack.server.GameState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to transport server response
 */
public class Answer implements Serializable {

    private static final long serialVersionUID = -686041796553415756L;

    private GameState state;
    private int bet;
    private List<Card> hand;
    private List<Card> dealerHand;
    private String errorMessage;
    private Result result;

    public Answer(Game game) {
        state = game.getState();
        bet = game.getBet();
        hand = new ArrayList<Card>(game.getHand());
        dealerHand = new ArrayList<Card>(game.getDealerHand());
        errorMessage = game.removeErrorMessage();
        result = game.getResult();
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

    public Result getResult() {
        return result;
    }

}
