package controllers;

import models.*;

import java.util.List;

public class GameController {
    public Game startGame(List<Player> players){
        Game game = Game.getBuilder()
                .setPlayers(players)
                .build();
        return game;
    }

    public GameState getGameState(Game game){
        return game.getGameState();
    }

    public Player getWinner(Game game){
        return game.getWinner();
    }
    public void printBoard(Game game){
        game.printBoard();
    }


    public Player getCurrentPlayer(Game game){
        return game.getCurrentPlayer();
    }

    public void undo(Game game){
        game.undo();
    }

    public void makeMove(Game game){
        game.makeMove();
    }
}
