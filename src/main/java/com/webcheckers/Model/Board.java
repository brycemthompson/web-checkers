package com.webcheckers.Model;

import javafx.geometry.Pos;

import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.abs;

/**
 * Java class object representing the board.
 * @author Clayton Pruitt : chp4145@rit.edu
 * @contributor Bryce Thompson : bxt6698@rit.edu
 */

public class Board implements Iterable<Row> {

    // all rows in the board
    ArrayList<Row> rows;
    private MovePacket backupMove;

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
     * Removes a Piece from the Space with the given coordinates.
     */
    public void removePieceFromSpace(int col, int row){
        getSpace(row, col).removePieceFromSpace();
    }

    /**
     * Moves a Piece.
     * @param mp an Object containing the Move and other useful information
     */
    public void movePiece(MovePacket mp){
        Move move = mp.getMove();
        // get all necessary coordinates
        int start_row = move.getStart().getRow();
        int start_cell = move.getStart().getCell();
        int end_row = move.getEnd().getRow();
        int end_cell = move.getEnd().getCell();

        // move the piece
        Piece p = rows.get(start_row).getSpace(start_cell).getPiece();
        rows.get(end_row).getSpace(end_cell).addPieceToSpace(p);
        rows.get(start_row).getSpace(start_cell).removePieceFromSpace();

        // set the backup move packet for this Board
        Move bmove = new Move(move.getEnd(), move.getStart());
        this.backupMove = new MovePacket(bmove, mp.getType(), mp.getJumpedPiece(), mp.getJumpedPiecePosition());
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
     * Helper function for getAllValidMoves that gets all simple moves.
     * @param player the Piece Color for the player
     * @return array list containing all simple moves
     */
    private ArrayList<MovePacket> getAllSimpleMoves(Piece.Color player){
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
        ArrayList<MovePacket> allValidMoves = new ArrayList<>();
        for (Space start : allStartingSpaces){
            // scan for valid spaces or spaces with pieces that can be jumped
            //System.out.println("For " + start + "...");
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
                        //System.out.println("\t" + cur + " is valid to move to.");
                        Move move = new Move(start.getPosition(), cur.getPosition());
                        MovePacket mp = new MovePacket(move, MovePacket.Type.SIMPLE);
                        allValidMoves.add(mp);
                    } else if (cur.hasPiece(opponent)){
                        // space has an opponent piece => check if it can be jumped
                        // TODO
                    }
                }
            }
        }

        return allValidMoves;
    }

    /**
     * Helper function for getAllValidMoves that gets all simple jump moves.
     * @param player the Piece Color for the player
     * @return array list containing all valid simple jump moves
     */
    private ArrayList<MovePacket> getAllSimpleJumpMoves(Piece.Color player){
        /*
        ALGORITHM:
        1. Check for any space that has a Piece belonging to the opponent.
        2. Check if the opponent's Piece is neighbored by any of our pieces.
            a) For every neighboring Piece, see if there is an empty space diagonally across the opponent's piece.
               If so, create a Move and add it to the list of valid Moves.
         */

        ArrayList<MovePacket> allValidMoves = new ArrayList<>();

        // scan the board, adding the Positions of the Opponent's pieces to a list
        ArrayList<Position> opponentPiecePositions = new ArrayList<>();
        ArrayList<Piece> opponentPieces = new ArrayList<>();
        for (int r = 0; r < rowsPerBoard; r++){
            for (int c = 0; c < rowsPerBoard; c++){
                if (getSpace(r, c).hasPiece(Piece.getOtherColor(player))){
                    System.out.println("Opponent piece at " + new Position(r, c));
                    opponentPiecePositions.add(new Position(r, c));
                    opponentPieces.add(getSpace(r, c).getPiece());
                }
            }
        }

        // scan each opponent Piece to see if a jump move is possible
        for (int i = 0; i < opponentPiecePositions.size(); i++) {

            // get the Position of the opponent Piece that we are currently scanning
            Position cur = opponentPiecePositions.get(i);
            Piece aaa = opponentPieces.get(i);
            System.out.println("==CURRENTLY EXAMINING " + cur + "==");
            // track any empty Space around this opponent's Piece in case we need them to make a Move
            ArrayList<Position> emptySpaces = new ArrayList<>();
            // also track any of our Pieces around this opponent's Piece
            ArrayList<Position> ourPieces = new ArrayList<>();

            for (int r = -1; r <= 1; r += 2) {
                for (int c = -1; c <= 1; c += 2) {
                    // get Space to scan
                    int scan_row = cur.getRow() + r;
                    int scan_col = cur.getCell() + c;
                    Space scan;
                    try{
                        scan = getSpace(scan_row, scan_col);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                    // check if Space is empty or has one of our Pieces
                    Position pos = new Position(scan_row, scan_col);
                    if (!scan.hasPiece()){
                        emptySpaces.add(pos);
                        System.out.println("Neighboring empty space found at " + new Position(scan_row, scan_col));
                        System.out.println("emptySpaces: " + emptySpaces);
                    } else if (scan.hasPiece(player)){
                        ourPieces.add(pos);
                        System.out.println("Neighboring player piece found at " + new Position(scan_row, scan_col));
                    }

                }
            }

            // check any of our Pieces to see if a jump can be made
            for (int j = 0; j < ourPieces.size(); j++){
                System.out.println("Comparing " + ourPieces.get(j) + " to...");
                System.out.println("(size of emptySpaces: " + emptySpaces.size() + ")");
                for (int k = 0; k < emptySpaces.size(); k++){
                    /*
                    An empty Space is valid to move to if the magnitude of the differences between ours and its rows
                    and column coordinates are both two.
                     */

                    Position possible_start = ourPieces.get(j);
                    Position possible_end = emptySpaces.get(k);

                    System.out.println("\t..." + possible_end);

                    if (abs(possible_start.getRow() - possible_end.getRow()) == 2 &&
                    abs(possible_start.getCell() - possible_end.getCell()) == 2){
                        Move move = new Move(possible_start, possible_end);
                        MovePacket mp = new MovePacket(move, MovePacket.Type.SIMPLE_JUMP, aaa, cur);
                        allValidMoves.add(mp);
                        System.out.println("**Valid move: " + new Move(possible_start, possible_end));
                    }
                }
            }
        }

        return allValidMoves;
    }

    /**
     * Calculates all possible and valid Moves that can be made for the given Player.
     * @param player the Piece Color for the player
     * @return array list containing all valid moves
     */
    public ArrayList<MovePacket> getAllValidMoves(Piece.Color player){

        ArrayList<MovePacket> allSimpleMoves = getAllSimpleMoves(player);
        ArrayList<MovePacket> allSimpleJumpMoves = getAllSimpleJumpMoves(player);

        // if there are jump moves present, they must be made
        if (allSimpleJumpMoves.size() != 0){
            return allSimpleJumpMoves;
        } else {
            return allSimpleMoves;
        }
    }

    /**
     * Getter for the backupMove to return the backupMove for the purpose to returning the moved piece to the original
     * location
     * @return backupMove: The MovePacket that contains the move to move the Piece back to before a submission is sent
     */
    public MovePacket getBackupMove()
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
