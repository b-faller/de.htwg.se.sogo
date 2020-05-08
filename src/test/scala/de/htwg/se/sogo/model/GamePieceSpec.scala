package de.htwg.se.sogo.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GamePieceSpec extends AnyWordSpec with Matchers {
    "A GamePiece" when {
        "set to a color " should {
            val red_piece = GamePiece(GamePieceColor.RED)
            val blue_piece = GamePiece(GamePieceColor.BLUE)
            "have color RED" in {
                red_piece.color should be(GamePieceColor.RED)
            }
            "print its color" in {
                red_piece.toString should be("R")
                blue_piece.toString should be("B")
            }
        }
    }
}
