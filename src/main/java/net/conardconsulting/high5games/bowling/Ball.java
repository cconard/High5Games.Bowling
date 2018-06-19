package net.conardconsulting.high5games.bowling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ball {
    private Character value;
    private int score;

    private static final Logger logger = LogManager.getLogger(Ball.class);

    public Ball(int ball) {
        this(Character.forDigit(ball, 10));
    }

    public Ball(char ball) {
        this(new Character(ball));
    }

    public Ball(Character ball) {
        if (ball == null) {
            throw new IllegalArgumentException();
        }

        String validValues = "X-/123456789";
        if (!validValues.contains(Character.toString(ball).toUpperCase())) {
            throw new IllegalArgumentException();
        }

        this.value = Character.toUpperCase(ball);
        this.score = score();
    }

    public Character getValue() {
        return value;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "value=" + value +
                ", score=" + score +
                '}';
    }

    public boolean isStrike() {
        return value.equals('X');
    }

    public boolean isSpare() {
        return value.equals('/');
    }

    public boolean isMiss() {
        return value.equals('-');
    }

    private int score() {
        if (Character.isDigit(this.value)) {
            return Character.getNumericValue(this.value);
        }

        if (this.isStrike()) return 10;

        if (this.isSpare()) return 10;

        if (this.isMiss()) return 0;

        // Any thing else is invalid
        return 0;
    }
}
