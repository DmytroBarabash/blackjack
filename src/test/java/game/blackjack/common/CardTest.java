package game.blackjack.common;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro Barabash
 * 2013-05-21 00:34
 */
public class CardTest {

    @Test
    public void testObtainDeck() {
        List<Card> deck = Card.obtainDeck();
        System.out.println(deck);
        assertEquals(deck.size(), Number.values().length * Suit.values().length);
    }

    @Test
    public void testShuffleDeck() {
        List<Card> deck = Card.obtainDeck();
        List<Card> shuffledDeck = Card.shuffleDeck();
        System.out.println(shuffledDeck);
        int i = 0;
        for (int j = 0; j < Number.values().length * Suit.values().length; j++){
            if (!deck.get(j).equals(shuffledDeck.get(j))) i++;
        }
        assertTrue(i > (Number.values().length * Suit.values().length / 4));
    }

}
