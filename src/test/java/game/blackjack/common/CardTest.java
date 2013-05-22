package game.blackjack.common;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardTest {

    @Test
    public void testObtainDeck() {
        List<Card> deck = Card.obtainDeck();
        assertEquals(deck.size(), Number.values().length * Suit.values().length);
    }

    @Test
    public void testShuffleDeck() {
        List<Card> deck = Card.obtainDeck();
        List<Card> shuffledDeck = Card.shuffleDeck();
        int i = 0;
        for (int j = 0; j < Number.values().length * Suit.values().length; j++) {
            if (!deck.get(j).equals(shuffledDeck.get(j))) i++;
        }
        assertTrue(i > 0);
    }

}
