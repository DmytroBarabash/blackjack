package game.blackjack.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro Barabash
 * 2013-05-21 00:08
 */
public class Card implements Serializable {

    private final Suit suit;
    private final Number number;

    public Card(Suit suit, Number number) {
        this.suit = suit;
        this.number = number;
    }

    public Suit getSuit() {
        return suit;
    }

    public Number getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        }
        if (another == this) {
            return true;
        }
        if (!another.getClass().equals(getClass())) {
            return false;
        }
        Card card = (Card) another;
        return number == card.getNumber() && suit == card.getSuit();
    }

    @Override
    public int hashCode() {
        return number.ordinal();
    }

    @Override
    public String toString() {
        return number + " of " + suit;
    }

    public static List<Card> obtainDeck() {
        List<Card> deck = new ArrayList<Card>(Suit.values().length * Number.values().length);
        for (Suit suit : Suit.values()) {
            for (Number number : Number.values()) {
                deck.add(new Card(suit, number));
            }
        }
        return deck;
    }

    public static List<Card> shuffleDeck() {
        List<Card> deck = obtainDeck();
        Collections.shuffle(deck);
        return deck;
    }

    public static int calculateTotal(List<Card> hand) {
        int i = 0;
        for (Card card : hand) {
            i += card.getNumber().weight;
        }
        return i;
    }

}
