package de.htwg.se.sogo.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GamePieceSpec extends AnyWordSpec with Matchers {
    "A GamePiece" when {
        "not set to any value " should {
            val red_piece = GamePiece(GamePieceColor.RED)
            "have color RED" in {
                red_piece.color should be(GamePieceColor.RED)
            }
        }
    }
}
