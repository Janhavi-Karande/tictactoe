package startegies;

import models.*;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{

    @Override
    public Move makeMove(Board board, List<Player> players){

        // check any empty cell and make move there
        for(List<Cell> row : board.getBoard()){
            for(Cell cell : row){
                if(cell.getCellState().equals(CellState.EMPTY)){

                    return new Move(cell, null);
                }

            }
        }
        return null;
    }
}
