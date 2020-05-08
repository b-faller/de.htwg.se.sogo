package de.htwg.se.sogo.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GameBoardSpec extends AnyFlatSpec with Matchers {
        "A GameBoard" should "be Empty when new" in {
            val board = new GameBoard(4,4,4)
            board.retrievePiece(3,3,3) should be(None)
        }
        it should "accept and be able to retrieve pieces" in {
            val board = new GameBoard(4,4,4)
            val piece = new GamePiece(GamePieceColor.RED)
            board.placePiece(Some(piece), (3,3,3))
            board.retrievePiece(3,3,3) should be(Some(piece))
            board.retrievePiece(2,1,0) should be(None)
        }
        it should "return a string representation of itself" in {
            val board = new GameBoard(3, 3, 3)
            val piece_r = new GamePiece(GamePieceColor.RED)
            val piece_b = new GamePiece(GamePieceColor.BLUE)
            board.placePiece(Some(piece_r), (0, 0, 0))
            board.placePiece(Some(piece_b), (2, 2, 2))
            board.toString should be(
"""Plane 0
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
    }
