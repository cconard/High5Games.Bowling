package net.conardconsulting.high5games.bowling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private String gameScore;
    private ArrayList<Frame> frameList;

    private static final Logger logger = LogManager.getLogger(Game.class);

    public Game(String gameScore) throws IllegalArgumentException {
        if (gameScore == null) {
            throw new IllegalArgumentException("Score is NULL");
        }

        this.gameScore = gameScore.toUpperCase().trim();
    }

    public String getGameScore() {
        return gameScore;
    }

    public void processGame() throws IllegalArgumentException {
        if (this.gameScore == null || this.gameScore.isEmpty()) {
            throw new IllegalArgumentException("Score is Null or Empty");
        }

        buildFrameList();

        if (frameList.isEmpty()) {
            logger.error("Unable to buildFrameList Score: " + gameScore);
            throw new IllegalArgumentException("Error Parsing Score: " + gameScore);
        }

        if (frameList.size() < 10 || frameList.size() > 11) {
            logger.error("Score has one or more invalid frames: " + gameScore);
            throw new IllegalArgumentException("Score has one or more invalid frames: " + gameScore);
        }

        logger.debug("Parsed Frame List [" + frameList.size() + "]: " + frameList.toString());

        processBonusBalls();

        if (frameList.size() != 10) {
            logger.error("Error Processing Bonus Balls: " + gameScore);
            throw new IllegalArgumentException("Error Processing Bonus Balls: " + gameScore);
        }

        logger.debug("Frame List with Bonus Balls [" + frameList.size() + "]: " + frameList.toString());
    }

    public int score() {
        int score = 0;

        for (Frame frame: frameList) {
            score += frame.score();
        }

        return score;
    }

    private void buildFrameList() {
        frameList = new ArrayList<Frame>();

        ArrayList<String> frameSplitList = new ArrayList<String>(Arrays.asList(this.gameScore.split("\\|", -1)));

        logger.debug("frameList Split: " + frameSplitList.toString());

        if (frameSplitList.size() < 11) {
            System.err.println("Not Enough Frames or Invalid List of Scores");
            return;
        }

        // Assume there are 12 frames, 10 game frames, empty frame, bonus frame
        for (int idx = 0; idx < 12; idx++) {
            // Catch if no bonus frame
            if (idx > frameSplitList.size()) {
                break;
            }

            String item = frameSplitList.get(idx);

            // Check for empty game frames
            if (item.isEmpty() && idx < 10) {
                throw new IllegalArgumentException("Empty Game Frame: " + (idx + 1));
            }

            // Filter out the empty frame between 10th and bonus frame and possible empty bonus frame
            if (item.isEmpty()) {
                continue;
            }

            Frame frame;
            try {
                frame = new Frame(item);
            } catch (IllegalArgumentException e) {
                logger.error("Error Processing Frame: " + item, e);
                continue;
            }

            frameList.add(frame);
        }
    }

    private void processBonusBalls() {
        for (int i = 0; i < 10; i++) {
            Frame frame = frameList.get(i);

            if (frame.isStrike()) {
                int bonusFrameIdx = i + 1;

                if (bonusFrameIdx >= frameList.size()) {
                    throw new ArrayIndexOutOfBoundsException("No Bonus Frame to add to 10th frame.");
                }

                Frame bonusFrame = frameList.get(bonusFrameIdx);

                // Just add the spare and call it good (represents 2 balls)
                if (bonusFrame.isSpare()) {
                    frame.addBonusBall(bonusFrame.getBall(1));
                    continue;
                }

                frame.addBonusBall(bonusFrame.getBall(0));

                // If there is a 2nd ball in the frame, use it
                if (bonusFrame.ballCount() > 1) {
                    frame.addBonusBall(bonusFrame.getBall(1));
                    continue;
                }

                // No 2nd ball, so take first ball from next frame
                bonusFrameIdx = i + 2;
                bonusFrame = frameList.get(bonusFrameIdx);

                frame.addBonusBall(bonusFrame.getBall(0));

                continue;
            }

            if (frame.isSpare()) {
                int bonusFrameIdx = i + 1;

                if (bonusFrameIdx >= frameList.size()) {
                    throw new ArrayIndexOutOfBoundsException("No Bonus Frame to add to 10th frame.");
                }

                Frame bonusFrame = frameList.get(bonusFrameIdx);

                frame.addBonusBall(bonusFrame.getBall(0));

                continue;
            }

            // nothing to do
        }

        // Remove bonus frame now that it's been added to 10th frame
        if (frameList.size() == 11) {
            frameList.remove(10);
        }
    }
}
