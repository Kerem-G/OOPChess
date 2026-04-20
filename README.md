# OOP Chess

    Names: Jacob Lehman and Kerem Gurkan
    Java Version: openJDK 21

# CSCI 4448/5448 - OOP Chess Project

## Project Description

A fully playable two-player chess game built with JavaFX, designed to demonstrate core object-oriented design principles and patterns. The game enforces legal moves for all pieces, detects check, checkmate, and stalemate, and notifies observers of game events through an event bus.

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

**Where:** `MoveStrategy` interface, implemented by `PawnStrategy`, `RookStrategy`, `BishopStrategy`, `QueenStrategy`, `KnightStrategy`, `KingStrategy`, and the abstract `SlidingMoveStrategy`.

**How:** `ChessPiece` holds a `MoveStrategy` injected at construction time and delegates all move calculation to it via `legalMoves()`. Each piece type has its own strategy class encapsulating its unique movement rules. Swapping or extending movement behavior requires no changes to `ChessPiece`.

### 2. Observer Pattern

**Where:** `GameObserver` interface, `EventBus` singleton, `CheckObserver` concrete observer.

**How:** `ChessGame.doMove()` posts events (`"MOVE"`, `"CHECK"`, `"CHECKMATE"`, `"STALEMATE"`) to the `EventBus` after each move. Any class implementing `GameObserver` can attach to the bus and react to events. `ChessGame` has no knowledge of its observers, it only knows about the `EventBus`.

### 3. Singleton Pattern

**Where:** `EventBus.getInstance()`

**How:** `EventBus` has a private constructor and exposes a single shared instance through `getInstance()`. This ensures all parts of the application post to and receive from the same event channel, regardless of where they are in the codebase.

### 4. Factory Pattern

**Where:** `ChessPieceFactory.createPiece(PieceColor, PieceType)`

**How:** All piece creation is centralized in `ChessPieceFactory`, which handles wiring each piece type to its correct `MoveStrategy`. No other class calls `new ChessPiece(...)` directly. Adding a new piece type or changing a strategy is a one-line change in the factory.

---

## Object-Oriented Principles

**Coding to abstractions:**
- `ChessPiece` depends on `MoveStrategy` (interface), never on a concrete strategy class.
- `EventBus` depends on `GameObserver` (interface), never on `CheckObserver` directly.

**Polymorphism:**
- Calling `exampleChessPiece.legalMoves()` on any `ChessPiece` produces correct movement for that piece type at runtime. A rook slides straight, a knight jumps in an L shape, a pawn moves forward and captures diagonally, all through the same method call.
- `RookStrategy`, `BishopStrategy`, and `QueenStrategy` all extend `SlidingMoveStrategy`, inheriting shared sliding logic while each overriding `moveList()` with their own direction sets.

**Dependency injection:**
- `ChessPiece` receives its `MoveStrategy` through its constructor.
- `ChessBoardView` receives a `ChessGame` through its constructor.
- `ChessPieceFactory` injects the correct strategy into each piece at creation time.

---

## Test Coverage

Tests are located in `src/test/java/org/chess/` and cover all non-GUI classes. Key test cases include:

| Test Class | What it covers |
|---|---|
| `PawnStrategyTest` | Forward movement, two-step from start row, blocking, diagonal captures, black pawn direction |
| `ChessGameTest` | Turn enforcement, legal/illegal moves, check detection, checkmate, stalemate, EventBus events |
| `EventBusTest` | Singleton identity, observer notification, detach behavior |
| `GameBoardTest` | Board setup, piece placement, move/clear, findKing |
| `RookStrategyTest` | Moves straight, not diagonally |
| `BishopStrategyTest` | Moves diagonally, not straight |
| `KnightStrategyTest` | L-shaped jumps, jumps over pieces, captures and blocks |
| `KingStrategyTest` | One step in any direction, captures, blocked by friendlies |
| `SlidingMoveStrategyTest` | Multi-square sliding, stops at friendly and enemy pieces |
| `CheckObserverTest` | Receives check, checkmate, and stalemate events |

---

## Future Work

- Castling and en passant are planned for a future milestone.
- Piece images are planned; pieces are currently displayed as letters (uppercase = white, lowercase = black).

## Attributions

The icons for the chess pieces were taken from Green Chess with permission under a Creative Commons Attribute and Share Alike license. The link to the website can be found here: https://greenchess.net/info.php?item=downloads
