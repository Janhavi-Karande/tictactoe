package startegies;

import models.*;

import java.util.List;

public class HardBotPlayingStrategy implements BotPlayingStrategy{
    Character botSymbol;
    Character humanSymbol;
    Player botPlayer = null;
    Player humanPlayer = null;

    @Override
    public Move makeMove(Board board, List<Player> players){

        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for(Player player : players){
            if(player.getPlayerType().equals(PlayerType.BOT)){
                botPlayer = player;
                botSymbol = player.getSymbol().getPlayerSymbol();
            }
            if(player.getPlayerType().equals(PlayerType.HUMAN)){
                humanPlayer = player;
                humanSymbol = player.getSymbol().getPlayerSymbol();
            }
        }

        // traverse all cells of the board
        for(List<Cell> row: board.getBoard()){
            for(Cell cell : row){
                if(cell.getCellState().equals(CellState.EMPTY)){

                    // set the cell state as Filled
                    cell.setCellState(CellState.FILLED);
                    // set the player
                    cell.setPlayer(botPlayer);

                    int score = minimax(board, 0, false);

                    // undo the move
                    cell.setPlayer(null);
                    cell.setCellState(CellState.EMPTY);

                    if (score == -100) {
                        return new Move(new Cell(cell.getRow(), cell.getCol()), botPlayer);
                    }

                    if(bestScore < score){
                        bestScore = score;
                        //bestMove.getCell().setRow(cell.getRow());
                        //bestMove.getCell().setCol(cell.getCol());
                        bestMove = new Move(new Cell(cell.getRow(), cell.getCol()), botPlayer);
                    }

                }
            }
        }

        return bestMove;
    }

    private int minimax(Board board, int depth, boolean isMax){
        int score = evaluate(board);


        if(score == 10)
            return score - depth;

        if(score == -10)
            return score + depth;

        if(!isMovesLeft(board))
            return 0;

        //System.out.println("Evaluating board at depth " + depth + ": " + score);

        if(isMax){
            int bestValue = Integer.MIN_VALUE;

            for(List<Cell> row : board.getBoard()){
                for(Cell cell : row){
                    if(cell.getCellState().equals(CellState.EMPTY)){

                        // set the cell state as Filled
                        cell.setCellState(CellState.FILLED);
                        // set the player as bot
                        cell.setPlayer(botPlayer);

                        bestValue = Math.max(bestValue, minimax(board, depth+1, false));

                        // undo the move
                       cell.setCellState(CellState.EMPTY);
                       cell.setPlayer(null);
                    }
                }
            }
            return bestValue;
        }

        else{
            int bestValue = Integer.MAX_VALUE;

            for(List<Cell> row : board.getBoard()){
                for(Cell cell : row){
                    if(cell.getCellState().equals(CellState.EMPTY)){

                        // set the cell state as Filled
                        cell.setCellState(CellState.FILLED);
                        // set the player as human
                        cell.setPlayer(humanPlayer);

                        bestValue = Math.min(bestValue, minimax(board, depth+1, true));

                        // undo the move
                        cell.setCellState(CellState.EMPTY);
                        cell.setPlayer(null);
                    }
                }
            }
            return bestValue;
        }

    }

    private boolean isMovesLeft(Board board){
        for(List<Cell> row: board.getBoard()) {
            for (Cell cell : row) {
                if (cell.getCellState().equals(CellState.EMPTY)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int evaluate(Board board){
        if(checkWinner(board, botSymbol))
            return 10;
        else if(checkWinner(board, humanSymbol))
            return -10;

        // New logic: If opponent is one move away from winning, prioritize blocking
        if(isOneMoveAwayFromWinning(board, humanSymbol))
            return -100; // Prioritize blocking over neutral moves

        return 0;
    }

    private boolean isOneMoveAwayFromWinning(Board board, Character playerSym) {
        List<List<Cell>> cells = board.getBoard();
        int dimension = board.getDimension();

        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++) {
                if(cells.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                    // Simulate placing the opponent's symbol
                    cells.get(i).get(j).setCellState(CellState.FILLED);
                    cells.get(i).get(j).setPlayer(humanPlayer);

                    boolean canWin = checkWinner(board, playerSym);

                    // Undo the move
                    cells.get(i).get(j).setCellState(CellState.EMPTY);
                    cells.get(i).get(j).setPlayer(null);

                    if(canWin) return true;
                }
            }
        }
        return false;
    }

    private boolean checkWinner(Board board, Character playerSym){
        List<List<Cell>> cells = board.getBoard();
        int dimension = board.getDimension();

        // check winner in every row and column
        for(int i = 0; i<dimension; i++){
            boolean rowWin = true;

            for(int j=0; j<dimension; j++){
                if(cells.get(i).get(j).getPlayer() == null
                        || cells.get(i).get(j).getPlayer().getSymbol().getPlayerSymbol() != playerSym){
                    rowWin = false;
                    break;
                }

            }

                if(rowWin )
                    return true;
        }

        for(int i = 0; i<dimension; i++){
            boolean colWin = true;

            for(int j=0; j<dimension; j++){

                if(cells.get(j).get(i).getPlayer() == null
                        || cells.get(j).get(i).getPlayer().getSymbol().getPlayerSymbol() != playerSym){
                    colWin = false;
                    break;
                }

            }

            if(colWin )
                return true;
        }

        // check winner in both diagonal
        boolean rightDiagonalWin = true, leftDiagonalWin = true;
        for(int i=0; i<dimension; i++){
            if(cells.get(i).get(i).getPlayer() == null
            || cells.get(i).get(i).getPlayer().getSymbol().getPlayerSymbol() != playerSym){
                rightDiagonalWin = false;
                break;
            }
        }


        for(int i=0; i<dimension; i++){
            if(cells.get(i).get(dimension - 1 -i).getPlayer() == null
                    || cells.get(i).get(dimension - 1 -i).getPlayer().getSymbol().getPlayerSymbol() != playerSym){
                leftDiagonalWin = false;
                break;
            }
        }

        return leftDiagonalWin || rightDiagonalWin;

    }

}
