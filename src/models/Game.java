package models;

import java.util.*;
import models.*;

public class Game {
    private Board board;
    private List<Move> moves;
    private List<Player> players;
    private Player winner;
    private GameState gameState;
    private int nextPlayerIndex;

    private final Map<Integer, HashMap<Character, Integer>> rowMaps = new HashMap<>();
    private final Map<Integer, HashMap<Character, Integer>> colMaps = new HashMap<>();
    private final Map<Character, Integer> rightDiagonal = new HashMap<>();
    private final Map<Character, Integer> leftDiagonal = new HashMap<>();

    private Game(List<Player> players ){
        this.players = players;
        this.board = new Board(3);
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
        this.winner = null;
        this.nextPlayerIndex = 0;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public void printBoard(){
        board.printBoard();
    }

    public Player getCurrentPlayer(){

        // shuffle the players list to get first player to make the move
        if(board.getBoard().isEmpty())
            Collections.shuffle(players);

        // get the current player
        Player currentPlayer = players.get(nextPlayerIndex);
        return currentPlayer;
    }

    public void makeMove(){

        // get the current player
        Player currentPlayer = getCurrentPlayer();
        Move playerMove = currentPlayer.makeMove(board, players);

        // validate the move player wants to make, only when player type is human
        while(playerMove != null && !validateMove(playerMove)){
            System.out.println("Invalid move, please retry!!");
            playerMove = currentPlayer.makeMove(board, players);
        }

        Move finalMove = null;
        // move is valid, so apply it to the board
        if(playerMove != null) {
            int row = playerMove.getCell().getRow();
            int col = playerMove.getCell().getCol();
            Cell cell = board.getBoard().get(row).get(col);
            cell.setCellState(CellState.FILLED);
            cell.setPlayer(currentPlayer);
            finalMove = new Move(cell, currentPlayer);
        }

        // add current move in the moves list if the player is human
        if(finalMove != null) {
            moves.add(finalMove);
        }


        if(playerMove == null || moves.size() == board.getDimension() * board.getDimension()){
            gameState = GameState.DRAW;
            winner = null;
        }

        // check if the current move is winning move
        else if(checkWinner(finalMove)){
            gameState = GameState.ENDED;
            winner = currentPlayer;
        }


        nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
    }

    private boolean validateMove(Move move){
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Cell cell = board.getBoard().get(row).get(col);

        if( row<0 || row> getBoard().getDimension()-1 || col<0 || col> board.getDimension()-1
        || cell.getCellState().equals(CellState.FILLED)){
            return false;
        }

        return true;
    }

    private boolean checkWinner(Move move){
        return checkRowWinner(move) || checkColWinner(move) || checkDiagonalWinner(move);
    }

    private boolean checkRowWinner(Move move){
        int row = move.getCell().getRow();
        Character sym = move.getPlayer().getSymbol().getPlayerSymbol();

        if(!rowMaps.containsKey(row)){
            rowMaps.put(row, new HashMap<>());
        }

        Map<Character, Integer> currRowMap = rowMaps.get(row);

        if(!currRowMap.containsKey(sym)){
            currRowMap.put(sym, 0);
        }

        currRowMap.put(sym, currRowMap.get(sym) + 1);

        if(currRowMap.get(sym).equals(board.getDimension()))
            return true;

        return false;
    }

    private boolean checkColWinner(Move move){
        int col = move.getCell().getCol();
        Character sym = move.getPlayer().getSymbol().getPlayerSymbol();

        if(!colMaps.containsKey(col)){
            colMaps.put(col, new HashMap<>());
        }

        Map<Character, Integer> currColMap = colMaps.get(col);

        if(!currColMap.containsKey(sym)){
            currColMap.put(sym, 0);
        }
        currColMap.put(sym, currColMap.get(sym) + 1);

        if(currColMap.get(sym).equals(board.getDimension())){
            return true;
        }

        return false;
    }

    private boolean checkDiagonalWinner(Move move){
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Character sym = move.getPlayer().getSymbol().getPlayerSymbol();

        // left to right diagonal
        if(row == col){
            if(!leftDiagonal.containsKey(sym)){
                leftDiagonal.put(sym, 0);
            }
            leftDiagonal.put(sym, leftDiagonal.get(sym) + 1);
        }

        // right to left diagonal
        if(row + col == board.getDimension()-1){
            if(!rightDiagonal.containsKey(sym)){
                rightDiagonal.put(sym, 0);
            }
            rightDiagonal.put(sym, rightDiagonal.get(sym) + 1);
        }

        if(row == col && leftDiagonal.get(sym).equals(board.getDimension()))
            return true;

        if(row+col == board.getDimension()-1 && rightDiagonal.get(sym).equals(board.getDimension()))
            return true;

        return false;
    }

    public void undo(){

        // get the last move of the human player
        Move lastHumanMove = null;
        for(Move move : moves.reversed()){
            if(move.getPlayer().getPlayerType().equals(PlayerType.HUMAN)){
                lastHumanMove = move;
                break;
            }

        }

        if(moves.isEmpty() || lastHumanMove == null){
            System.out.println("You can't undo now.");
            return;
        }


        //check last move content
        int flag = 0;
        int row = lastHumanMove.getCell().getRow();
        int col = lastHumanMove.getCell().getCol();
        Character sym = lastHumanMove.getPlayer().getSymbol().getPlayerSymbol();

        // remove from left diagonal
        if(row == col){

            if(leftDiagonal.get(sym) == 1){
                leftDiagonal.remove(sym);

            }
            else{
                leftDiagonal.put(sym, leftDiagonal.get(sym) - 1);
            }

            // remove from the row and column
            undoRow(sym, row);
            undoCol(sym, col);
            flag = 1;
        }

        // remove from right diagonal
        if(row+col == board.getDimension()-1){
            if(rightDiagonal.get(sym) == 1){
                rightDiagonal.remove(sym);
            }
            else{
                rightDiagonal.put(sym, rightDiagonal.get(sym)-1);
            }

            // remove from the row and column
            if(flag == 0) {
                undoRow(sym, row);
                undoCol(sym, col);
            }
        }
        else{
            undoCol(sym, col);
            undoRow(sym, row);
        }

        Cell currCell = board.getBoard().get(row).get(col);
        currCell.setCellState(CellState.EMPTY);
        currCell.setPlayer(null);

        // remove last human's move from the moves list
        moves.remove(lastHumanMove);

        // current player will also change after undo
        nextPlayerIndex = Math.abs((nextPlayerIndex - 1) % players.size());

    }

    public void undoRow(Character sym, int row){
        // remove from rowMaps
        Map<Character, Integer> currRowMap = rowMaps.get(row);

        if(currRowMap.get(sym) == 1){
            currRowMap.remove(sym);
        }
        else{
            currRowMap.put(sym, currRowMap.get(sym)-1);
        }
    }

    public void undoCol(Character sym, int col){
        // remove from colMaps
        Map<Character, Integer> currColMap = colMaps.get(col);

        if(currColMap.get(sym) == 1){
            currColMap.remove(sym);
        }
        else{
            currColMap.put(sym, currColMap.get(sym)-1);
        }
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public static class Builder{
        private List<Player> players;

        private Builder(){
            this.players = new ArrayList<>();
        }

        public List<Player> getPlayers() {
            return players;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        // validate unique symbol for every player
        private void validateUniqueSymbol(){
            int count=0;

            Set<Character> symbols = new HashSet<>();

            for(Player player: players){
                if(!symbols.contains(player.getSymbol().getPlayerSymbol())){
                    symbols.add(player.getSymbol().getPlayerSymbol());
                    count++;
                }
            }

            if(count != symbols.size()){
                throw new RuntimeException("Every player must have unique symbols!");
            }
        }

        // only one bot is allowed per game
        private void validateBotCount(){
            int count=0;
            for(Player player:players){
                if(player.getPlayerType().equals(PlayerType.BOT))
                    count++;
            }

            if(count > 1){
                throw new RuntimeException("Only one bot is allowed per game.");
            }
        }

        private void validations(){
            validateUniqueSymbol();
            validateBotCount();
        }

        public Game build(){
            validations();
            return new Game(players);
        }
    }
}
