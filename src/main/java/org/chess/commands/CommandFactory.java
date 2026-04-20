package org.chess.commands;

import org.chess.GameBoard;

public class CommandFactory {
    public ChessCommand newMoveCommand(GameBoard board, int rowFrom, int colFrom, int rowTo, int colTo){
        return new MoveCommand(board, rowFrom, colFrom, rowTo, colTo);
    }
}
