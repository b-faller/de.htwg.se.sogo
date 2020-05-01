package de.htwg.se.sogo.model

import de.htwg.se.sogo.model.GamePieceColor
import org.scalatest.{Matchers, WordSpec}

class GamePieceSpec extends WordSpec with Matchers {
    "A GamePiece" when {
        "not set to any value " should {
            val red_piece = GamePiece(GamePieceColor.RED)
            "have color RED" in {
                red_piece.color should be(GamePieceColor.RED)
            }
        }
    }
}
