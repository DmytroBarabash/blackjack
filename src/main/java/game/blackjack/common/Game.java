package game.blackjack.common;

import game.blackjack.server.GameState;

import java.util.ArrayList;
import java.util.List;

import static game.blackjack.server.GameState.*;
import static game.blackjack.common.Result.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro Barabash
 * 2013-05-21 01:30
 */

/**
 *
 */
public class Game {

    private List<Card> deck = Card.shuffleDeck();

    private GameState state;
    private int bet;
    private List<Card> hand;
    private List<Card> dealerHand;
    private String errorMessage;
    private Result result;

    {
        state = START;
        result = UNDEFINED;
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

    private void chooseWinner() {
        int total = Card.calculateTotal(hand);
        if (total > Constants.TWENTY_ONE) {
            result = LOSE;
            bet = 0;
            return;
        }

        while (Card.calculateTotal(dealerHand) < 17) {
            giveCard(dealerHand);
        }
        int dealerTotal = Card.calculateTotal(dealerHand);
        if (dealerTotal > Constants.TWENTY_ONE) {
            result = WON;
            bet = bet * 2;
        } else if (dealerTotal == Constants.TWENTY_ONE && total == Constants.TWENTY_ONE) {
            result = DRAW;
        } else if (dealerTotal == Constants.TWENTY_ONE) {
            result = LOSE;
            bet = 0;
        } else if (total == Constants.TWENTY_ONE){
            result = WON;
            bet = bet + bet % 2 * 3;
        } else if (total > dealerTotal) {
            result = WON;
            bet = bet * 2;
        } else {
            result = LOSE;
            bet = 0;
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
                        if (total >= Constants.TWENTY_ONE) {
                            state = FINISH;
                            chooseWinner();
                        }
                        break;
                    }
                    case STAND: {
                        state = FINISH;
                        chooseWinner();
                        break;
                    }
                    case DOUBLE: {
                        state = FINISH;
                        bet = bet * 2;
                        giveCard(hand);
                        chooseWinner();
                        break;
                    }
                    default: {
                        errorMessage = "unknown command";
                        break;
                    }
                }
                break;
            }
            default: {
                errorMessage = "wrong state";
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

    public String removeErrorMessage() {
        String tmp = errorMessage;
        errorMessage = null;
        return tmp;
    }

    public Result getResult() {
        return result;
    }

}
