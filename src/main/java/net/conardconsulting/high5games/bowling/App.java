package net.conardconsulting.high5games.bowling;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Arrays;
import java.util.Scanner;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        App bowlingScore = new App();

        Configurator.setRootLevel(Level.ERROR);

        logger.debug("Args: " + Arrays.toString(args));

        // If no arguments, assume console input
        if (args.length == 0) {
            bowlingScore.consoleInput();
        } else {
            bowlingScore.parseCLIOptions(args);
        }

        System.exit(0);
    }

    private App() {
    }

    private String consoleInput() {
        Scanner scanner = new Scanner(System.in);

        String scoreLine = scanner.nextLine();

        if ((scoreLine == null) || scoreLine.isEmpty()) {
            System.out.println("Nothing to Process, Score: 0");
            return null;
        }

        logger.debug("Console Input: " + scoreLine);

        processScore(scoreLine.toUpperCase().trim());

        return scoreLine;
    }

    private void parseCLIOptions(String[] args) {
        Options options = new Options();

        Option file = new Option("f", "file", true, "Input File");
        file.setRequired(false);
        options.addOption(file);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);

            logger.debug("Command Line: " + cmd);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Bowling Score", options);

            System.exit(1);
        }
    }

    private void processFile(String fileName) {

    }

    private void processScore(String score) {
        logger.debug("Processing Score: " + score);

        Game game = new Game(score);

        game.processGame();

        int gameScore = game.score();

        System.out.println("Game Score: " + gameScore);
    }
}
