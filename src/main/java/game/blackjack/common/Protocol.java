package game.blackjack.common;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro Barabash
 * 2013-05-21 01:30
 */
public class Protocol {

    public String processInput(String s) {
        if (s == null) {
            return "Null received";
        }
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }

}
