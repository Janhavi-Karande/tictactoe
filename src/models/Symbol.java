package models;

public class Symbol {
    private Character playerSymbol;
    private String color;

    public Symbol(Character playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public Character getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(Character playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
