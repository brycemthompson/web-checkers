package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.PI;
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

    private void addRedPiece(int c, int r){
        addPieceToSpace(new Piece(Piece.Type.SINGLE, Piece.Color.RED), c, r);
    }

    private void addWhitePiece(int c, int r){
        addPieceToSpace(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), c, r);
    }

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


        /*addWhitePiece(1, 0);
        addWhitePiece(3, 0);
        addWhitePiece(5, 0);
        addWhitePiece(7, 0);
        addWhitePiece(0, 1);
        addWhitePiece(6, 1);
        addWhitePiece(1, 2);
        addWhitePiece(3, 2);
        addWhitePiece(5, 2);
        addWhitePiece(7, 2);
        addWhitePiece(5, 4);

        addRedPiece(0, 3);
        addRedPiece(4, 5);
        addRedPiece(6, 5);
        addRedPiece(1, 6);
        addRedPiece(3, 6);
        addRedPiece(5, 6);
        addRedPiece(7, 6);
        addRedPiece(0, 7);
        addRedPiece(2, 7);
        addRedPiece(4, 7);
        addRedPiece(6, 7);*/
    }

    /**
     * Adds the given Piece to the given coordinates
     * @param piece: The piece to add the the space
     * @param col: The column of the space
     * @param row: The row of the space
     */
    public void addPieceToSpace(Piece piece, int col, int row){
        this.rows.get(row).addPieceToSpace(piece, col);
    }

    /**
     * Removes a Piece from the Space with the given coordinates
     * @param col: Column of the space to remove a piece from
     * @param row: Row of the space to remove a piece from
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
        switch (mp.getType()){
            case SIMPLE:
                this.backupMove = new MovePacket(
                        Move.reverse(mp.getMove()));
                break;
            case SIMPLE_JUMP:
                this.backupMove = new MovePacket(
                        Move.reverse(mp.getMove()),
                        mp.getJumpedPiece());
                break;
            case MULTIPLE_JUMP:
                this.backupMove = new MovePacket(
                        Move.reverse(mp.getMove()),
                        mp.getJumpedPieces());
                break;
        }
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
     * Gets any Pieces neighboring the given Position.
     * @param position the Position to find neighboring Pieces around
     * @return an ArrayList of all Pieces adjacent to the given Position
     */
    public ArrayList<PieceWithPosition> getNeighboringPieces(Position position){

        int row = position.getRow();
        int col = position.getCell();
        ArrayList<PieceWithPosition> pieces = new ArrayList<>();

        for (int r = -1; r < 2; r += 2){
            for (int c = -1; c < 2; c += 2){
                Space space = null;
                try{
                    space = getSpace(row + r, col + c);
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
                Piece piece = space.getPiece();
                if (piece != null){
                    Position piecePosition = new Position(row + r, col + c);
                    pieces.add(new PieceWithPosition(piece, piecePosition));
                }
            }
        }

        return pieces;

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
            for (int r = -1; r <= 1; r++){
                for (int c = -1; c <= 1; c++){
                    Space cur = null;
                    try {
                        cur = getSpace(start.getCellIdy() + r, start.getCellIdx() + c);
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    if (cur.isValid()){
                        // space is a valid space to move to
                        Move move = new Move(start.getPosition(), cur.getPosition());
                        MovePacket mp = new MovePacket(move);
                        allValidMoves.add(mp);
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
                    //System.out.println("Opponent piece at " + new Position(r, c));
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
            //System.out.println("==CURRENTLY EXAMINING " + cur + "==");
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
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    // check if Space is empty or has one of our Pieces
                    Position pos = new Position(scan_row, scan_col);
                    if (!scan.hasPiece()){
                        emptySpaces.add(pos);
                        //System.out.println("Neighboring empty space found at " + new Position(scan_row, scan_col));
                        //System.out.println("emptySpaces: " + emptySpaces);
                    } else if (scan.hasPiece(player)){
                        ourPieces.add(pos);
                        //System.out.println("Neighboring player piece found at " + new Position(scan_row, scan_col));
                    }

                }
            }

            // check any of our Pieces to see if a jump can be made
            for (int j = 0; j < ourPieces.size(); j++){
                //System.out.println("Comparing " + ourPieces.get(j) + " to...");
                //System.out.println("(size of emptySpaces: " + emptySpaces.size() + ")");
                for (int k = 0; k < emptySpaces.size(); k++){
                    /*
                    An empty Space is valid to move to if the magnitude of the differences between ours and its rows
                    and column coordinates are both two.
                     */

                    Position possible_start = ourPieces.get(j);
                    Position possible_end = emptySpaces.get(k);

                    //System.out.println("\t..." + possible_end);

                    if (abs(possible_start.getRow() - possible_end.getRow()) == 2 &&
                    abs(possible_start.getCell() - possible_end.getCell()) == 2){
                        Move move = new Move(possible_start, possible_end);
                        MovePacket mp = new MovePacket(move, new PieceWithPosition(aaa, cur));
                        allValidMoves.add(mp);
                        //System.out.println("**Valid move: " + new Move(possible_start, possible_end));
                    }
                }
            }
        }
        return allValidMoves;
    }

    /**
     * Recursive method for finding all multiple jump moves a given Piece can make.
     * This method is used as a helper method for getAllMultipleJumpMoves().
     * @param startingPosition the Position of the Piece to find the multiple jump move for
     * @param currentPosition the current Position of the Space we are looking for jump moves from
     * @param jumpedPieces a running list of all Pieces we've jumped thus far
     * @return an ArrayList of all Moves possible from the given starting position
     */
    private ArrayList<MovePacket> findAllMultiJumpMovesForAPiece(Position startingPosition,
                                                                 Position currentPosition,
                                                                 ArrayList<PieceWithPosition> jumpedPieces)
    {

        System.out.println("ENTERING RECURSION");

        ArrayList<MovePacket> allValidMoves = new ArrayList<>();

        // find all diagonally adjacent Pieces to the current Position
        ArrayList<PieceWithPosition> neighboringPieces = getNeighboringPieces(currentPosition);
        // collect any of the diagonally adjacent Pieces that belong to us
        Piece playerPiece = getSpace(startingPosition.getRow(), startingPosition.getCell()).getPiece();
        Piece.Color playerPieceColor = playerPiece.getColor();
        ArrayList<PieceWithPosition> ourPieces = new ArrayList<>();
        for (PieceWithPosition pwp : neighboringPieces){
            Piece piece = pwp.getPiece();
            if (piece.getColor() == playerPieceColor){
                ourPieces.add(pwp);
            }
        }
        // remove jumped opponent Pieces and our Pieces from the adjacency list
        neighboringPieces.removeAll(ourPieces);
        neighboringPieces.removeAll(jumpedPieces);

        /*
        If there are diagonally adjacent unjumped opponent Pieces, recursively run this method until we find one
        that cannot be jumped.
        If there are no diagonally adjacent unjumped opponent Pieces, then there are no more Pieces to jump over.
         */
        if (neighboringPieces.size() != 0){
            for (PieceWithPosition neighboringPiece : neighboringPieces){

                System.out.println("Current adjacency check: " + neighboringPiece.getPosition());

                // find the diagonal this neighboring Piece is along relative to our current Position
                int diagonalRowDifference = neighboringPiece.getRow() - currentPosition.getRow();
                int diagonalCellDifference = neighboringPiece.getCell() - currentPosition.getCell();

                // check if the next Space along the diagonal is empty (or exists)
                int nextSpaceRow = neighboringPiece.getRow() + diagonalRowDifference;
                int nextSpaceCell = neighboringPiece.getCell() + diagonalCellDifference;

                Space nextSpaceAlongDiagonal = null;
                try {
                    nextSpaceAlongDiagonal = getSpace(nextSpaceRow, nextSpaceCell);
                } catch (IndexOutOfBoundsException e){
                    if (!currentPosition.equals(startingPosition)) {
                        Position endingPosition = currentPosition;
                        Move move = new Move(startingPosition, endingPosition);
                        System.out.println("c) NEW MOVE: " + move);
                        MovePacket mp = new MovePacket(move, jumpedPieces);
                        allValidMoves.add(mp);
                    }
                    continue;
                }

                boolean spaceIsEmpty = !nextSpaceAlongDiagonal.hasPiece();

                // if there is an empty Space along the diagonal then calculate the next jump; otherwise end this jump.
                if (spaceIsEmpty){
                    System.out.println("Empty space found at cell " + nextSpaceCell + " and row: " + nextSpaceRow);
                    jumpedPieces.add(neighboringPiece);
                    ArrayList<MovePacket> newValidMoves = findAllMultiJumpMovesForAPiece(startingPosition,
                            new Position(nextSpaceRow, nextSpaceCell),
                            jumpedPieces);
                    allValidMoves.addAll(newValidMoves);
                } else {
                    if (!currentPosition.equals(startingPosition)) {
                        System.out.println(nextSpaceAlongDiagonal + " is NOT empty!");
                        Position endingPosition = currentPosition;
                        Move move = new Move(startingPosition, endingPosition);
                        System.out.println("b) NEW MOVE: " + move);
                        MovePacket mp = new MovePacket(move, jumpedPieces);
                        allValidMoves.add(mp);
                    }
                }

            }
        } else {
            // there are no more opponent Pieces to jump
            if (!currentPosition.equals(startingPosition)) {
                Position endingPosition = currentPosition;
                Move move = new Move(startingPosition, endingPosition);
                System.out.println("a) NEW MOVE: " + move);
                MovePacket mp = new MovePacket(move, jumpedPieces);
                allValidMoves.add(mp);
            }
        }

        return allValidMoves;
    }

    /**
     * Helper function for getAllValidMoves that gets all multiple jump moves.
     * @param player the Piece Color for the player
     * @return array list containing all valid multiple jump moves
     */
    private ArrayList<MovePacket> getAllMultipleJumpMoves(Piece.Color player){

        ArrayList<MovePacket> allValidMoves = new ArrayList<>();

        // use indexing to find player's Pieces so we can derive coordinates
        for (int r = 0; r < rowsPerBoard; r++){
            for (int c = 0; c < rowsPerBoard; c++){
                // check if this space has a piece belonging to the player
                Space currentSpace = getSpace(r, c);
                Piece pieceOnSpace = currentSpace.getPiece();

                // if it does, find all possible multiple jump moves this Piece can make
                if (pieceOnSpace != null){

                    // begin recursion
                    Position startingPosition = new Position(r, c);
                    Position currentPosition = new Position(r, c);
                    System.out.println("======================================");
                    System.out.println("SCANNING SPACE: " + currentSpace);
                    System.out.println("======================================");
                    ArrayList<MovePacket>  allMultiJumps = findAllMultiJumpMovesForAPiece(
                            startingPosition,
                            currentPosition,
                            new ArrayList<PieceWithPosition>()
                    );

                    // collect all found moves into our list of valid moves
                    allValidMoves.addAll(allMultiJumps);

                }
            }
        }

        // The algorithm finds ALL jump Moves, so prune any Moves that are too short.

        ArrayList<MovePacket> smallMoves = new ArrayList<>();

        for (MovePacket mp : allValidMoves){
            if (mp.getMove().getDistance() <= Math.sqrt(8)){
                smallMoves.add(mp);
            }
        }

        allValidMoves.removeAll(smallMoves);
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
        ArrayList<MovePacket> allMultiJumpMoves = getAllMultipleJumpMoves(player);

        System.out.println("allSimpleMoves: " + allSimpleMoves.size());
        System.out.println("allSimpleJumpMoves: " + allSimpleJumpMoves.size());
        System.out.println("allMultiJumpMoves: " + allMultiJumpMoves.size());

        // priority: multiple jump > simple jump > simple move
        if (allMultiJumpMoves.size() != 0){
            return allMultiJumpMoves;
        }
        if (allSimpleJumpMoves.size() != 0){
            return allSimpleJumpMoves;
        } else {
            return allSimpleMoves;
        }
    }

    public boolean checkForWin(Piece.Color currentPlayerColor)
    {
        boolean winFlag = true;
        Piece.Color opponentColor;
        Piece.Color checkColor;
        Piece currentPiece;
        Space currentSpace;

        if(currentPlayerColor == Piece.Color.RED)
            opponentColor = Piece.Color.WHITE;
        else
            opponentColor = Piece.Color.RED;

        for(int r = 0; r < rowsPerBoard; r++)
        {
            for(int c = 0; c < rowsPerBoard; c++)
            {
                currentSpace = rows.get(r).getSpace(c);
                currentPiece = currentSpace.getPiece();
                if(currentPiece != null) {
                    checkColor = currentSpace.getPiece().getColor();
                    if (checkColor.equals(opponentColor))
                        winFlag = false;
                }
            }
        }
        return winFlag;
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
     * Gets the red player
     * @return redPlayer
     */
    public Player getRedPlayer(){
        return this.redPlayer;
    }

    /**
     * Gets the white player
     * @return whitePlayer
     */
    public Player getWhitePlayer(){
        return this.whitePlayer;
    }

    /**
     * Gets the active color
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
