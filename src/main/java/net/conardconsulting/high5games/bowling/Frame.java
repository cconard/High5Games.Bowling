package net.conardconsulting.high5games.bowling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Frame {
    private ArrayList<Ball> ballList;
    private ArrayList<Ball> bonusBallList;

    private static final Logger logger = LogManager.getLogger(Frame.class);

    public Frame (String frame) throws IllegalArgumentException {
        this.ballList = new ArrayList<>();
        this.bonusBallList = new ArrayList<>();

        parse(frame);
    }

    public int ballCount() {
        return ballList.size();
    }

    public Ball getBall(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > ballList.size()) {
            throw new IndexOutOfBoundsException();
        }

        return ballList.get(index);
    }

    public int bonusBallCount() {
        return bonusBallList.size();
    }

    public void addBonusBall(Ball ball) {
        bonusBallList.add(ball);
    }

    public int score() {
        int score = 0;

        if (isSpare() || isStrike()) {
            score += 10;
        } else {
            for (Ball ball : ballList) {
                score += ball.getScore();
            }
        }

        for (Ball ball : bonusBallList) {
            score += ball.getScore();
        }

        return score;
    }

    public boolean isStrike() {
        if (ballList.isEmpty()) return false;

        return ballList.get(0).isStrike();
    }

    public boolean isSpare() {
        if (ballList.isEmpty() || ballList.size() < 2) return false;

        return ballList.get(1).isSpare();
    }

    @Override
    public String toString() {
        return "Frame{" +
                "ballList=" + ballList +
                ", bonusBallList=" + bonusBallList +
                '}';
    }

    private void parse(String frame) throws IllegalArgumentException {
        if (frame.isEmpty()) {
            logger.error("Frame is Empty");
            throw new IllegalArgumentException("Frame is Empty");
        }

        char[] balls = frame.toCharArray();

        if (balls.length > 2) {
            logger.error("Frame has too many balls: " + frame);
            throw new IllegalArgumentException("Frame has too many balls");
        }

        this.ballList.add(new Ball(balls[0]));

        if (balls.length == 2) {
            this.ballList.add(new Ball(balls[1]));
        }
    }
}
