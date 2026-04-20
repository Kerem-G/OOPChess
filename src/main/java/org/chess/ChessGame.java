package org.chess;

import org.chess.commands.ChessCommand;
import org.chess.commands.CommandFactory;
import org.chess.observers.EventBus;
import org.chess.pieces.ChessPiece;
import org.chess.pieces.ChessPieceFactory;
import org.chess.pieces.PieceColor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ChessGame {
    private final GameBoard board;
    private final ChessPieceFactory pieceFactory;
    private final CommandFactory commandFactory;
    private PieceColor currentTurn;
    private final Deque<ChessCommand> history = new ArrayDeque<>();

    public ChessGame() {
        this.board = new GameBoard();
        this.pieceFactory = new ChessPieceFactory();
        this.commandFactory = new CommandFactory();
        this.currentTurn = PieceColor.WHITE;
        board.setupInitialPosition(pieceFactory);
    }

    public GameBoard getBoard() {
        return board;
    }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public boolean doMove(int rowFrom, int colFrom, int rowTo, int colTo) {
        ChessPiece piece = board.getPiece(rowFrom, colFrom);

        if (piece == null) {
            return false;
        }

        if (piece.getColor() != currentTurn) {
            return false;
        }

        List<int[]> moves = legalMoves(rowFrom, colFrom);
        if (!containsSquare(moves, rowTo, colTo)) {
            return false;
        }

        ChessCommand command = commandFactory.newMoveCommand(this.board,rowFrom, colFrom, rowTo, colTo);
        command.execute();
        history.push(command);
        currentTurn = currentTurn.opposite();
        EventBus.getInstance().postEvent("MOVE");

        if (isCheckmate()) {
            EventBus.getInstance().postEvent("CHECKMATE");
        } else if (isStalemate()) {
            EventBus.getInstance().postEvent("STALEMATE");
        } else if (isInCheck(currentTurn)) {
            EventBus.getInstance().postEvent("CHECK");
        }

        return true;
    }
    public boolean undoMove() {
        if (!history.isEmpty()) {
            ChessCommand command = history.pop();
            command.undo();
            currentTurn = currentTurn.opposite();
            EventBus.getInstance().postEvent("UNDO");
            return true;
        }
        return false;
    }

    private boolean containsSquare(List<int[]> squares, int row, int col) {
        for (int[] square : squares) {
            if (square != null && square.length == 2 && square[0] == row && square[1] == col) {
                return true;
            }
        }
        return false;
    }

    public boolean isInCheck(PieceColor color) {
        int[] kingPosition = board.findKing(color);
        if (kingPosition == null) {
            return false;
        }
        int kingRow = kingPosition[0];
        int kingCol = kingPosition[1];

        for (int row = 0; row < GameBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < GameBoard.BOARD_SIZE; col++) {
                ChessPiece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() != color) {
                    List<int[]> moves = piece.legalMoves(board, row, col, piece);
                    if (containsSquare(moves, kingRow, kingCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate() {
        return isInCheck(currentTurn) && hasNoLegalMoves();
    }

    public boolean isStalemate() {
        return !isInCheck(currentTurn) && hasNoLegalMoves();
    }

    private boolean hasNoLegalMoves() {
        for (int row = 0; row < GameBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < GameBoard.BOARD_SIZE; col++) {
                ChessPiece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() == currentTurn) {
                    if (!legalMoves(row, col).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<int[]> legalMoves(int row, int col) {
        ChessPiece piece = board.getPiece(row, col);
        if (piece == null || piece.getColor() != currentTurn) {
            return List.of();
        }

        List<int[]> candidateMoves = piece.legalMoves(board, row, col, piece);
        List<int[]> safeMoves = new ArrayList<>();

        for (int[] move : candidateMoves) {
            ChessPiece captured = board.getPiece(move[0], move[1]);
            board.movePiece(row, col, move[0], move[1]);
            if (!isInCheck(piece.getColor())) {
                safeMoves.add(move);
            }
            board.movePiece(move[0], move[1], row, col);
            board.setPiece(move[0], move[1], captured);
        }

        return safeMoves;
    }
}
