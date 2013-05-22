package game.blackjack.common;

public enum Number {

    C2(2), C3(3), C4(4), C5(5), C6(6), C7(7), C8(8), C9(9), C10(10), JACK(10), QUEEN(10), KING(10), ACE(11);
    
    public final int weight;

    Number(int weight) {
        this.weight = weight;
    }

}
