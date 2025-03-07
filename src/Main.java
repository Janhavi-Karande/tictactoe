import controllers.*;
import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // rules of the game
        System.out.println("GAME BEGINS!");
        System.out.println("These are the rules of the game:");
        System.out.println("1. Every player must have unique symbol.");
        System.out.println("2. Player can select symbol from the given list of symbols.");
        System.out.println("List of the symbols: ! @ # $ % ^ & * ~ +");
        System.out.println("3. Single player can play the game, other player is bot.");
        System.out.println("4. Player can select difficulty level of the bot.");
        System.out.println("5. Player can use undo once in a game and only with bot. \n");

        List<Player> players = new ArrayList<>();

        System.out.println("Do you want to play with bot?(y/n)");
        String isBot = sc.next();

        int playerCount = 2;
        if(isBot.equals("y")) {
            System.out.println("Select difficulty level of the bot.");
            System.out.println("Enter 1 -> Easy \n 2 -> Hard");
            int x = sc.nextInt();
            if(x == 1)
                players.add(new Bot("Scalar Bot", new Symbol('X'), PlayerType.BOT, BotDifficultyLevel.EASY));
            else
                players.add(new Bot("Scalar Bot", new Symbol('X'), PlayerType.BOT, BotDifficultyLevel.HARD));

            playerCount=1;
        }

        sc.nextLine();
        // take input for players names
        String[] playerNames = new String[playerCount];
        for(int i=0; i<playerCount; i++) {
            System.out.println("Please enter player " +(i+1)+ "'s name:");
            playerNames[i] = sc.nextLine();
        }

        //take input for players symbol
        System.out.println("\n Select one symbol for every player.");

        Character[] playerSymbols = new Character[playerCount];
        for(int i=0; i<playerCount; i++){
            System.out.println("Please enter player " +(i+1)+ "'s symbol from the given list:");
            playerSymbols[i] = sc.next().charAt(0);
        }

        // add players to the list
        for(int i=0; i<playerCount; i++) {
            players.add(new Player(playerNames[i], new Symbol(playerSymbols[i]), PlayerType.HUMAN));
        }

        GameController gameController = new GameController();
        Game game = gameController.startGame(players);

        int undoCount = 0;
        while(gameController.getGameState(game).equals(GameState.IN_PROGRESS)){
            gameController.printBoard(game);

                // ask player if they want to undo


                System.out.println("Do you want to undo(y/n):");
                String isUndo = sc.next();

            if (isUndo.equals("y") && undoCount == 0 && playerCount == 1) {
                gameController.undo(game);
                undoCount = 1;
                continue;
            }
            if(isUndo.equals("y") && undoCount > 0)
                System.out.println("You can only undo once.");

            if(isUndo.equals("y") && playerCount ==2)
                System.out.println("Undo is available only with bot. ");

            gameController.makeMove(game);
            System.out.println();
        }

        if(gameController.getGameState(game).equals(GameState.ENDED)){
            System.out.println(gameController.getWinner(game).getName()+ " has won the game.");
        }
        else {
            System.out.println("The game has draw.");
        }

        gameController.printBoard(game);
        System.out.println("DEBUG");
    }
}