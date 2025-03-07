package models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Cell>> board;
    private int dimension;

    public Board( int dimension) {
        // initialize empty board
        board = new ArrayList<>();

        for(int i=0; i<dimension; i++){
            board.add(new ArrayList<>());

            for(int j=0; j<dimension; j++)
                board.get(i).add(new Cell(i, j));
        }
        this.dimension = dimension;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void printBoard(){

        for(List<Cell> row: board){
            for(Cell cell: row){
                cell.printCell();
            }
            System.out.println();
        }
    }
}
