package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Java class object representing the board.
 * @author Clayton Pruitt : chp4145@rit.edu
 * @contributor Bryce Thompson : bxt6698@rit.edu
 */

public class Board implements Iterable<Row> {

    // all rows in the board
    ArrayList<Row> rows;
    private Move backupMove;

    // amount of rows in a board
    public static int rowsPerBoard = 8;

    // players
    private Player redPlayer;
    private Player whitePlayer;
    private Piece.Color activeColor;

    /**
     * Constructor. Automatically populates the board with the starting pieces.
     */
    public Board(){
        // populate board with rows
        rows = new ArrayList<Row>();
        for (int i = 0; i < rowsPerBoard; i++){
            rows.add(new Row(i));
        }

        // populate board with checkers in starting positions

        // white
        for (int r = 0; r < 3; r += 1){
            for (int c = (r + 1) % 2; c < rowsPerBoard; c += 2){
                addPieceToSpace(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), c, r);
            }
        }

        // red
        for (int r = rowsPerBoard - 3; r < rowsPerBoard; r += 1){
            for (int c = (r + 1) % 2; c < rowsPerBoard; c += 2){
                addPieceToSpace(new Piece(Piece.Type.SINGLE, Piece.Color.RED), c, r);
            }
        }
    }

    /**
     * Adds the given Piece to the given coordinates.
     */
    public void addPieceToSpace(Piece piece, int col, int row){
        this.rows.get(row).addPieceToSpace(piece, col);
    }

    /**
     * Moves a Piece.
     * @param move the Move object type containing the start and end
     */
    public void movePiece(Move move){
        // get all necessary coordinates
        int start_row = move.getStart().getRow();
        int start_cell = move.getStart().getCell();
        int end_row = move.getEnd().getRow();
        int end_cell = move.getEnd().getCell();

        // move the piece
        Piece p = rows.get(start_row).getSpace(start_cell).getPiece();
        rows.get(end_row).getSpace(end_cell).addPieceToSpace(p);
        rows.get(start_row).getSpace(start_cell).removePieceFromSpace();

        // set the backup move for this Board
        this.backupMove = new Move(move.getEnd(), move.getStart());
    }

    /**
     * Gets the Space with the given coordinates.
     * @param row the row the Space is in
     * @param cell the cell the Space is in
     * @return Space instance
     */
    public Space getSpace(int row, int cell){
        return this.rows.get(row).getSpace(cell);
    }

    /**
     * Calculates all possible and valid Moves that can be made for the given Player.
     * @param player the Piece Color for the player
     * @return array list containing all valid Moves
     */
    public ArrayList<Move> getAllValidMoves(Piece.Color player){

        // get opponent color
        Piece.Color opponent = Piece.getOtherColor(player);

        // scan each row and collect all spaces with pieces with the matching color
        ArrayList<Space> allStartingSpaces = new ArrayList<Space>();
        for (Row row : rows){
            for (Space space : row){
                if (space.hasPiece(player)){
                    allStartingSpaces.add(space);
                }
            }
        }

        // generate all valid Moves with the given start spaces
        ArrayList<Move> allValidMoves = new ArrayList<Move>();
        for (Space start : allStartingSpaces){
            // scan for valid spaces or spaces with pieces that can be jumped
            System.out.println("For " + start + "...");
            for (int r = -1; r <= 1; r++){
                for (int c = -1; c <= 1; c++){
                    Space cur = null;
                    try {
                        cur = getSpace(start.getCellIdy() + r, start.getCellIdx() + c);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    if (cur.isValid()){
                        // space is a valid space to move to
                        System.out.println("\t" + cur + " is valid to move to.");
                        allValidMoves.add(new Move(start.getPosition(), cur.getPosition()));
                    } else if (cur.hasPiece(opponent)){
                        // space has an opponent piece => check if it can be jumped
                        // TODO
                    }
                }
            }
        }

        // return list of valid moves
        return allValidMoves;
    }

    /**
     * Setter for the creation of a backup move for the purposes of undoing a move before a submission
     * @param move: The move to put in backupMove
     */
    public void setBackupMove(Move move)
    {
        this.backupMove = move;
    }

    /**
     * Getter for the backupMove to return the backupMove for the purpose to returning the moved piece to the original
     * location
     * @return backupMove: The Move to move the Piece back to before a submission is sent
     */
    public Move getBackupMove()
    {
        return this.backupMove;
    }

    /**
     * Sets the red Player to be the given Player.
     * @param player the Player with red pieces
     */
    public void setRedPlayer(Player player){
        this.redPlayer = player;
    }

    /**
     * Sets the white Player to be the given Player.
     * @param player the Player with white pieces
     */
    public void setWhitePlayer(Player player){
        this.whitePlayer = player;
    }

    /**
     * Sets the active color.
     * @param activeColor color to be active
     */
    public void setActiveColor(Piece.Color activeColor){
        this.activeColor = activeColor;
    }

    /**
     * Flips the active color of this Boardd.
     */
    public void flipActiveColor(){
        this.activeColor = Piece.getOtherColor(this.activeColor);
    }

    /**
     * @return redPlayer
     */
    public Player getRedPlayer(){
        return this.redPlayer;
    }

    /**
     * @return whitePlayer
     */
    public Player getWhitePlayer(){
        return this.whitePlayer;
    }

    /**
     * @return activeColor
     */
    public Piece.Color getActiveColor(){
        return this.activeColor;
    }

    /**
     * Override iterator function. Contains other functions for the iterator
     * @return it: The new iterator
     */
    @Override
    public Iterator<Row> iterator() {
        Iterator<Row> it = new Iterator<Row>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext(){
                return (currentIndex < rows.size() && rows.get(currentIndex) != null);
            }

            @Override
            public Row next(){
                return rows.get(currentIndex++);
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
