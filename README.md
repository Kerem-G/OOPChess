# OOP Chess

    Names: Jacob Lehman and Kerem Gurkan
    Java Version: openJDK 21

# CSCI 4448/5448 - OOP Chess Project

## Project Description

A fully playable two-player chess game built with JavaFX. The game enforces legal moves for all pieces, handles castling and pawn promotion, supports unlimited undo and redo, and notifies observers of game events through a shared event bus. Pieces are rendered as image sprites, and check, checkmate, and stalemate are surfaced to the player through a notification system.

To run the project if the project has never been run before:
```
./gradlew build
```

To run the project after it has been run before:
```
./gradlew run
```

---

## Design Patterns

### 1. Strategy Pattern

The Strategy pattern is used twice in this project: once for piece movement, and again for piece rendering.

**Where (movement):** `MoveStrategy` interface, implemented by `PawnStrategy`, `RookStrategy`, `BishopStrategy`, `QueenStrategy`, `KnightStrategy`, `KingStrategy`, and the abstract `SlidingMoveStrategy`.

**How (movement):** `ChessPiece` holds a `MoveStrategy` injected at construction time and delegates all move calculation to it via `legalMoves()`. Each piece type has its own strategy class encapsulating its unique movement rules. Swapping or extending movement behavior requires no changes to `ChessPiece`.

**Where (rendering):** `PieceRenderer` interface, implemented by `ImagePieceRenderer`.

**How (rendering):** `ChessPiece` also holds a `PieceRenderer` injected at construction time and delegates rendering to it via `render()`. The view simply calls `piece.render()` and gets back a JavaFX `ImageView`. It does not need to know how the piece is drawn. A different renderer (e.g., text-based or 3D) could be plugged in without changing the view or the piece.

### 2. Observer Pattern

**Where:** `GameObserver` interface, `EventBus` singleton, with concrete observers `CheckObserver` and `NotificationObserver`.

**How:** `ChessGame` posts events (`"MOVE"`, `"CHECK"`, `"CHECKMATE"`, `"STALEMATE"`, `"UNDO"`, `"REDO"`) to the `EventBus` after each game action. Any class implementing `GameObserver` can attach to the bus and react to events. `ChessGame` has no knowledge of its observers, it only knows about the `EventBus`. `NotificationObserver` listens for state-change events and updates the UI (status label for check, modal alert for checkmate and stalemate) without `ChessGame` ever knowing the UI exists.

### 3. Singleton Pattern

**Where:** `EventBus.getInstance()`

**How:** `EventBus` has a private constructor and exposes a single shared instance through `getInstance()`. This ensures all parts of the application post to and receive from the same event channel, regardless of where they are in the codebase.

### 4. Factory Pattern

**Where:** `ChessPieceFactory.createPiece(PieceColor, PieceType)` and `CommandFactory.newCommand(...)`.

**How:** All piece creation is centralized in `ChessPieceFactory`, which wires each piece to its correct `MoveStrategy` and `PieceRenderer`. All command creation is centralized in `CommandFactory`, which inspects the move and returns the right `ChessCommand` subtype (a king moving two squares becomes a `CastleCommand`, a pawn reaching the back rank becomes a `PromoteCommand`, anything else becomes a `MoveCommand`). No other class calls `new ChessPiece(...)` or `new MoveCommand(...)` directly.

### 5. Command Pattern

**Where:** `ChessCommand` interface in `org.chess.commands`, implemented by `MoveCommand`, `PromoteCommand`, and `CastleCommand`. Constructed via `CommandFactory`, executed by `ChessGame.doMove()`, stored in a history `Deque`, reversed by `ChessGame.undoMove()` and re-executed by `ChessGame.redoMove()`.

**How:** Every legal move is wrapped in a `ChessCommand` object that knows how to `execute()` itself and how to `undo()` itself. `ChessGame` keeps two deques (a history stack and a redo stack) and pushes/pops commands as the user makes moves, undoes, or redoes. Making any new move clears the redo stack so the user cannot redo into a branched timeline. Adding a new command type (e.g., `EnPassantCommand`) requires no changes to `ChessGame`.

---

## Object-Oriented Principles

**Coding to abstractions:**
- `ChessPiece` depends on `MoveStrategy` and `PieceRenderer` (interfaces), never on concrete classes.
- `EventBus` depends on `GameObserver` (interface), never on `CheckObserver` or `NotificationObserver` directly.
- `ChessGame` depends on `ChessCommand` (interface), never on a specific command subtype.
- `NotificationObserver` depends on a `Consumer<String>` callback, not on `ChessBoardView` or any JavaFX class. The view binds to it via a method reference.

**Polymorphism:**
- Calling `chessPiece.legalMoves()` on any `ChessPiece` produces correct movement for that piece type at runtime. A rook slides straight, a knight jumps in an L shape, a pawn moves forward and captures diagonally, all through the same method call.
- `RookStrategy`, `BishopStrategy`, and `QueenStrategy` all extend `SlidingMoveStrategy`, inheriting shared sliding logic while each overriding `moveList()` with their own direction sets.
- `command.execute()` and `command.undo()` work uniformly on any `ChessCommand`, even though `MoveCommand`, `PromoteCommand`, and `CastleCommand` mutate the board in very different ways.

**Dependency injection:**
- `ChessPiece` receives its `MoveStrategy` and `PieceRenderer` through its constructor.
- `ChessBoardView` receives a `ChessGame` through its constructor.
- `ChessPieceFactory` injects the correct strategy and renderer into each piece at creation time.
- `CommandFactory` receives a `ChessPieceFactory` so it can construct promoted pieces.
- `NotificationObserver` receives a `ChessGame` reference and a status-update `Consumer` through its constructor.

---

## Test Coverage

Tests are located in `src/test/java/org/chess/`, organized into subpackages mirroring the main source layout (`commands/`, `observers/`, `pieces/`, `strategies/`). Selected coverage:

| Test Class | What it covers |
|---|---|
| `ChessGameTest` | Turn enforcement, legal/illegal moves, check detection, checkmate, stalemate, EventBus events, undo/redo, promotion, castling in legal moves |
| `GameBoardTest` | Board setup, piece placement, move/clear, findKing |
| `commands/MoveCommandTest` | Execute moves and captures, undo restores both pieces, hasMoved flag tracking and restoration |
| `commands/PromoteCommandTest` | Pawn promotes to chosen piece type, undo restores the original pawn (not a fresh pawn), capture during promotion |
| `commands/CastleCommandTest` | Kingside and queenside castle move both king and rook, undo restores both pieces, hasMoved tracking and restoration |
| `commands/CommandFactoryTest` | Branching: returns the correct `ChessCommand` subtype for a regular move, promotion, or castle |
| `observers/EventBusTest` | Singleton identity, observer notification, detach behavior |
| `observers/CheckObserverTest` | Receives check, checkmate, and stalemate events |
| `observers/NotificationObserverTest` | Sets status text on CHECK; clears status text on MOVE/UNDO/REDO |
| `pieces/ChessPieceTest` | Construction, color and type accessors |
| `pieces/PieceColorTest`, `pieces/PieceTypeTest` | Enum behavior |
| `strategies/PawnStrategyTest` | Forward movement, two-step from start row, blocking, diagonal captures, black pawn direction |
| `strategies/RookStrategyTest` | Moves straight, not diagonally |
| `strategies/BishopStrategyTest` | Moves diagonally, not straight |
| `strategies/KnightStrategyTest` | L-shaped jumps, jumps over pieces, captures and blocks |
| `strategies/KingStrategyTest` | One step in any direction, captures, blocked by friendlies |
| `strategies/QueenStrategyTest` | Combined rook and bishop movement |
| `strategies/SlidingMoveStrategyTest` | Multi-square sliding, stops at friendly and enemy pieces |

---

## Attributions

The icons for the chess pieces were taken from Green Chess with permission under a Creative Commons Attribute and Share Alike license. The link to the website can be found here: https://greenchess.net/info.php?item=downloads
