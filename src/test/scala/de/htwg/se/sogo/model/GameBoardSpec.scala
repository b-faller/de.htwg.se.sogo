package de.htwg.se.sogo.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GameBoardSpec extends AnyFlatSpec with Matchers {
  "A GameBoard" should "be Empty when new" in {
    val board = new GameBoard(4, 4, 4)
    board.retrievePiece(3, 3, 3) should be(None)
  }
  it should "be created using a factory method" in {
    GameBoardFactory.apply("small") should be(new GameBoard(3, 3, 3))
    GameBoardFactory.apply("standard") should be(new GameBoard(4, 4, 4))
  }
  it should "accept and be able to retrieve pieces" in {
    var board = new GameBoard(4, 4, 4)
    val piece1 = new GamePiece(GamePieceColor.RED)
    val piece2 = new GamePiece(GamePieceColor.BLUE)
    board = board.placePiece(piece1, (3, 3))
    board = board.placePiece(piece2, (3, 3))
    board.retrievePiece(3, 3, 0) should be(Some(piece1))
    board.retrievePiece(3, 3, 1) should be(Some(piece2))
    board.retrievePiece(3, 3, 2) should be(None)
    board.retrievePiece(2, 1, 0) should be(None)
  }
  it should "return null when placing at an invalid position" in {
    var board = new GameBoard(4, 4, 4)
    val piece1 = new GamePiece(GamePieceColor.RED)
    board.placePiece(piece1, (4, 3)) should be(null)
    board.placePiece(piece1, (4, 4)) should be(null)
    board.placePiece(piece1, (-1, -3)) should be(null)
  }
  it should "return a string representation of itself" in {
    var board = new GameBoard(3, 3, 3)
    val piece_r = new GamePiece(GamePieceColor.RED)
    val piece_b = new GamePiece(GamePieceColor.BLUE)
    board = board.placePiece(Some(piece_r), (0, 0, 0))
    board = board.placePiece(Some(piece_b), (2, 2, 2))
    board.toString should be("""Plane 0
-------
|R| | |
| | | |
| | | |

Plane 1
-------
| | | |
| | | |
| | | |

Plane 2
-------
| | | |
| | | |
| | |B|

""")
  }
  it should "be winnable" in {
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWon(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (0, 0, 0))
    board = board.placePiece(Some(piece), (1, 0, 0))
    board.hasWon(GamePieceColor.RED) should be(true)
    board.hasWon(GamePieceColor.BLUE) should be(false)
  }
  it should "be winnable in x-direction in a single layer" in {
    // Plane 1
    // -------
    // | | |
    // |R|R|
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonX(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (0, 1, 1))
    board = board.placePiece(Some(piece), (1, 1, 1))
    board.hasWonX(GamePieceColor.RED) should be(true)
    board.hasWonX(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable in y-direction in a single layer" in {
    // Plane 1
    // -------
    // | |R|
    // | |R|
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonY(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (1, 0, 1))
    board = board.placePiece(Some(piece), (1, 1, 1))
    board.hasWonY(GamePieceColor.RED) should be(true)
    board.hasWonY(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable in x-direction ascending" in {
    // Plane 0
    // -------
    // |R| |
    // | | |
    //
    // Plane 1
    // -------
    // | |R|
    // | | |
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonXAscending(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (0, 0, 0))
    board = board.placePiece(Some(piece), (1, 0, 1))
    board.hasWonXAscending(GamePieceColor.RED) should be(true)
    board.hasWonXAscending(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable in x-direction descending" in {
    // Plane 0
    // -------
    // | |R|
    // | | |
    //
    // Plane 1
    // -------
    // |R| |
    // | | |
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonXDescending(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (0, 0, 1))
    board = board.placePiece(Some(piece), (1, 0, 0))
    board.hasWonXDescending(GamePieceColor.RED) should be(true)
    board.hasWonXDescending(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable in y-direction ascending" in {
    // Plane 0
    // -------
    // |R| |
    // | | |
    //
    // Plane 1
    // -------
    // | | |
    // |R| |
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonYAscending(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (0, 0, 0))
    board = board.placePiece(Some(piece), (0, 1, 1))
    board.hasWonYAscending(GamePieceColor.RED) should be(true)
    board.hasWonYAscending(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable in y-direction descending" in {
    // Plane 0
    // -------
    // | | |
    // | |R|
    //
    // Plane 1
    // -------
    // | |R|
    // | | |
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonYDescending(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (1, 0, 1))
    board = board.placePiece(Some(piece), (1, 1, 0))
    board.hasWonYDescending(GamePieceColor.RED) should be(true)
    board.hasWonYDescending(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable diagonally upper left to lower right" in {
    // Plane 1
    // -------
    // |R| |
    // | |R|
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonDiagonally(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (0, 0, 1))
    board = board.placePiece(Some(piece), (1, 1, 1))
    board.hasWonDiagonally(GamePieceColor.RED) should be(true)
    board.hasWonDiagonally(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable diagonally upper right to lower left" in {
    // Plane 1
    // -------
    // | |R|
    // |R| |
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonDiagonally(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (1, 0, 1))
    board = board.placePiece(Some(piece), (0, 1, 1))
    board.hasWonDiagonally(GamePieceColor.RED) should be(true)
    board.hasWonDiagonally(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable diagonally upper left to lower right ascending" in {
    // Plane 0
    // -------
    // |R| |
    // | | |
    //
    // Plane 1
    // -------
    // | | |
    // | |R|
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonDiagonallyAscending(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (0, 0, 0))
    board = board.placePiece(Some(piece), (1, 1, 1))
    board.hasWonDiagonallyAscending(GamePieceColor.RED) should be(true)
    board.hasWonDiagonallyAscending(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable diagonally upper left to lower right descending" in {
    // Plane 0
    // -------
    // | | |
    // | |R|
    //
    // Plane 1
    // -------
    // |R| |
    // | | |
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonDiagonallyDescending(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (0, 0, 1))
    board = board.placePiece(Some(piece), (1, 1, 0))
    board.hasWonDiagonallyDescending(GamePieceColor.RED) should be(true)
    board.hasWonDiagonallyDescending(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable diagonally upper right to lower left ascending" in {
    // Plane 0
    // -------
    // | |R|
    // | | |
    //
    // Plane 1
    // -------
    // | | |
    // |R| |
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonDiagonallyAscending(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (1, 0, 0))
    board = board.placePiece(Some(piece), (0, 1, 1))
    board.hasWonDiagonallyAscending(GamePieceColor.RED) should be(true)
    board.hasWonDiagonallyAscending(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
  it should "be winnable diagonally upper right to lower left descending" in {
    // Plane 0
    // -------
    // | | |
    // |R| |
    //
    // Plane 1
    // -------
    // | |R|
    // | | |
    var board = new GameBoard(2, 2, 2)
    val piece = new GamePiece(GamePieceColor.RED)
    board.hasWonDiagonallyDescending(GamePieceColor.RED) should be(false)
    board = board.placePiece(Some(piece), (1, 0, 1))
    board = board.placePiece(Some(piece), (0, 1, 0))
    board.hasWonDiagonallyDescending(GamePieceColor.RED) should be(true)
    board.hasWonDiagonallyDescending(GamePieceColor.BLUE) should be(false)
    board.hasWon(GamePieceColor.RED) should be(true)
  }
}
