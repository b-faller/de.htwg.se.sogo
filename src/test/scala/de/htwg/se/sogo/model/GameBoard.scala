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
    }
