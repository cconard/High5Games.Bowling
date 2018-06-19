package net.conardconsulting.high5games.bowling;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BallTest {
    @Test
    public void constructorValid() {
        Character expected = 'X';

        Ball ball = new Ball('X');
        assertEquals(expected, ball.getValue());

        ball = new Ball('x');
        assertEquals(expected, ball.getValue());

        expected = '-';

        ball = new Ball('-');
        assertEquals(expected, ball.getValue());

        expected = '/';

        ball = new Ball('/');
        assertEquals(expected, ball.getValue());

        for (int i = 1; i < 10; i++) {
            expected = Character.forDigit(i, 10);

            ball = new Ball(i);
            assertEquals(expected, ball.getValue());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidNull() {
        new Ball(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNumberHigh() {
        new Ball(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidNumberLow() {
        new Ball(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidCharacter() {
        new Ball('A');
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidSymbol() {
        new Ball('=');
    }

    @Test
    public void getScore() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void isStrike() {
    }

    @Test
    public void isSpare() {
    }

    @Test
    public void isMiss() {
    }
}