package models;

import java.util.List;
import java.util.Scanner;

public class Player {
    private String name;
    private int age;
    private Symbol symbol;
    private PlayerType playerType;
    private Scanner sc;

    public Player(String name, Symbol symbol, PlayerType playerType) {
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
        this.sc = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType(){
        return playerType;
    }

    public void setPlayerType(PlayerType playerType){
        this.playerType = playerType;
    }

    public Move makeMove(Board board, List<Player> players){
        System.out.println("This is " +this.name+ "`s move.");

        // take inputs from the user for the row and col
        System.out.println("Please enter the row number:");
        int row = sc.nextInt();
        System.out.println("Please enter the column number:");
        int col = sc.nextInt();

        Cell cell = new Cell(row, col);

        return new Move(cell, this);
    }
}
