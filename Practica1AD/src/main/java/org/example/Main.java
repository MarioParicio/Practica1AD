package org.example;


import java.io.*;
import java.util.*;


import lombok.extern.slf4j.Slf4j;

@Slf4j

public class Main {

    static FileReader reader;
    static BufferedWriter writer;
    static Scanner sc = new Scanner(System.in);
    static ResourceBundle currentBundle;
    static ResourceBundle bundle = ResourceBundle.getBundle("ApplicationMessages");
    static ResourceBundle bundleEN = ResourceBundle.getBundle("ApplicationMessagesEN");
    static File config = new File("config/config.properties");
    static Properties properties = new Properties();
    static String choice = " ";
    static int AIchoice = 0;
    static int playerchoice = 0;
    static int gamesDifficult = 0;

    static boolean gameEnd = false;

    public static void main(String[] args) {

        log.trace("Starting program" );

        while (!gameEnd) {

            loadConfig();
            menu();


        }
        log.trace("Game ended" );
    }

    private static void menu() {
        log.trace("Entering in function menu");
        println("Welcome");
        println("Menu_Option");
        validateNumber(1,2,3,4);
        log.trace("After validating the number we check the player option");
        switch (choice) {
            case "1" :
                log.trace("Player choice is startGame");
                startGame();
                break;
            case "2" :
                log.trace("Player choice is changeLanguage");
                changeLanguage();
                break;
            case "3" :
                log.trace("Player choice is changeDifficulty");
                changeDifficulty();
                break;
            case "4" :
                log.trace("Player choice is exit");
                exit();
                break;

        }

    }

    private static void changeDifficulty(){
        log.trace("Entering in function changeDifficulty");
        println("ChangeDifficulty");
        validateNumber(1,2);
        try {
            log.trace("Checking what difficulty to switch to");
            if (choice.equals("1")) {
                properties.setProperty("difficulty", "1");
                properties.store(new BufferedWriter(new FileWriter("config/config.properties")), "ChangeLanguage");
                log.trace("Changing to easy difficulty");
            }
            else if (choice.equals("2")) properties.setProperty("difficulty", "2");
            properties.store(new BufferedWriter(new FileWriter("config/config.properties")), "ChangeLanguage");
            log.trace("Changing to hard difficulty");
        } catch (IOException e) {
            log.error("IOException occurred");
            throw new RuntimeException(e);
        }


    }

    private static void exit() {
        log.trace("Entering exit function");
        println("Exit");
        log.trace("Changing gameEnd true to end the program");
        gameEnd = true;
    }

    private static void changeLanguage()  {
        log.trace("Entering in function changeLanguage");
        println("ChangeLanguage");

        validateNumber(1,2);

        try {
            log.trace("Checking what difficulty to switch to");
            if (choice.equals("1")){
                log.trace("Switch language to spanish");
                properties.setProperty("language", "espaniol");
                properties.store(new BufferedWriter(new FileWriter("config/config.properties")), "ChangeLanguage");

                loadConfig();

            }
            else if (choice.equals("2")) {
                log.trace("Switch language to english");
                System.out.println("Cambiando idioma");
                properties.setProperty("language", "english");
                properties.store(new BufferedWriter(new FileWriter("config/config.properties")), "ChangeLanguage");
                loadConfig();
            }
        } catch (IOException e) {
            log.error("IOException occurred");
            throw new RuntimeException(e);
        }


    }

    private static void startGame() {
        log.trace("Entering in function startGame");
        println("GameStart");
        validateNumber(1,2,3);
        log.trace("Setting playerchoice to int, and calculating Aichoice");
        playerchoice = Integer.parseInt(choice);
        AIchoice = (int) (Math.random() * 3 + 1);
        println("AIAlreadyChosen");
        getWinner();

    }

    private static void getWinner() {
        log.trace("Entering in function getWinner");
        if (properties.getProperty("difficulty").equals("2")) gamesDifficult += 1;
        if (gamesDifficult == 4) gamesDifficult = 0;
        if (properties.getProperty("difficulty").equals("1")  || gamesDifficult == 1  || gamesDifficult == 2) {
            log.trace("Getting real winner, if difficulty is easy or we have played 2 or 3 games in difficulty mode");
            switch (playerchoice) {
                case 1://Piedra
                    if (AIchoice == 1) {
                        log.trace("Game ended with a draw");
                        println("Draw");
                    } else if (AIchoice == 2) {
                        log.trace("The computer wins");
                        println("WinnerComputer");
                    } else if (AIchoice == 3) {
                        log.trace("The player wins");
                        println("WinnerPlayer");
                    }

                    break;
                case 2://Papel
                    if (AIchoice == 1) {
                        log.trace("The player wins");
                        println("WinnerPlayer");
                    } else if (AIchoice == 2) {
                        log.trace("Game ended with a draw");
                        println("Draw");
                    } else if (AIchoice == 3) {
                        log.trace("The computer wins");
                        println("WinnerComputer");
                    }

                    break;
                case 3://Tijera
                    if (AIchoice == 1) {
                        log.trace("The computer wins");
                        println("WinnerComputer");
                    } else if (AIchoice == 2) {
                        log.trace("The player wins");
                        println("WinnerPlayer");
                    } else if (AIchoice == 3) {
                        log.trace("Game ended with a draw");
                        println("Draw");
                    }
                    break;
            }
        }
        if (properties.getProperty("difficulty").equals("2") && gamesDifficult == 0 || gamesDifficult == 3){
            log.trace("The computer wins because of the difficulty");
            println("WinnerComputer");
        }


    }

    private static void validateNumber(int ... args) {
        log.trace("Entering in function validateNumber");
        log.trace("Asking user for a number and validating it");
        while (!sc.hasNextInt() ) {
            log.trace("Validating if input is a number");
            println("ValidValue");
            choice = sc.next();
            log.trace("The user input is" + choice);
        }
        log.trace("The input is a number");
        choice = sc.next();

            if (valueNotValid(Integer.parseInt(choice), args)) {
                log.trace("Value not valid, returning start of functionº");
                println("ValidValue");
                validateNumber(args);
            }


    }

    private static boolean valueNotValid(int value, int ... args) {
        log.trace("Entering in function valueNotValid");
        return Arrays.stream(args).noneMatch(value1 -> value1 == value);

    }

    private static void loadConfig() {
        log.trace("Load config function");
        try {

            log.trace("Reading configuration and loading it");
            reader = new FileReader(config);
            properties.load(reader);

            log.trace("Reading language configuration and loading language");
            if (properties.getProperty("language").equals("espaniol")){
                log.trace("Language configuration set to spanish");
                currentBundle = bundle;
            } else if (properties.getProperty("language").equals("english")){
                log.trace("Language configuration set to english");
                currentBundle = bundleEN;
            }


        } catch (FileNotFoundException e) {
            log.error("File not found");
            System.out.println("File not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("IOException occurred");
            System.out.println("Error reading file");
            throw new RuntimeException(e);
        } finally {
            try {
                log.trace("Closing reader");
                reader.close();
            } catch (IOException e) {
                log.trace("IOException occurred closing the reader");
                throw new RuntimeException(e);
            }
        }
    }


    private static void println(String key) {
        log.trace("Entering in function println and printing string from configuration");
        System.out.println(currentBundle.getString(key));

    }

/* No implementation
   private static void print(String key) {
        log.trace("Entering in function println and printing string from configuration");

        System.out.println(currentBundle.getString(key));

    }*/
}