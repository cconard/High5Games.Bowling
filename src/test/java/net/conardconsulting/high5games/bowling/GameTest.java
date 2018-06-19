package net.conardconsulting.high5games.bowling;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
    @Test
    public void scorePerfectGame() {
        String score = "X|X|X|X|X|X|X|X|X|X||XX";

        Game game = new Game(score);
        game.processGame();

        assertEquals(300, game.score());
    }

    @Test
    public void scoreNoBonusBalls() {
        String score = "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||";

        Game game = new Game(score);
        game.processGame();

        assertEquals(90, game.score());
    }

    @Test
    public void scoreAllSpares() {
        String score = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5";

        Game game = new Game(score);
        game.processGame();

        assertEquals(150, game.score());
    }

    @Test
    public void scoreMix() {
        String score = "X|7/|9-|X|-8|8/|-6|X|X|X||81";

        Game game = new Game(score);
        game.processGame();

        assertEquals(167, game.score());
    }

    @Test
    public void testConstructor() {
        String score = "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||";

        Game game = new Game(score);

        assertEquals(score, game.getGameScore());
    }

    @Test
    public void processGame() {
    }

    @Test
    public void score() {
    }
}