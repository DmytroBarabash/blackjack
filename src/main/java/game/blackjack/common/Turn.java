package game.blackjack.common;

public enum Turn {

    HIT("h"), STAND("s"), DOUBLE("d"), OTHER("");

    public final String code;

    Turn(String code) {
        this.code = code;
    }

    static Turn getTurn(String code) {
        for (Turn turn : Turn.values()) {
            if (turn.code.equals(code)) {
                return turn;
            }
        }
        return OTHER;
    }

}
