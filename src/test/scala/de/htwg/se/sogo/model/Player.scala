package de.htwg.se.sogo.model

import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
    "A Player" when {
        "new" should {
            val player = Player("Sum Ting Wong", GamePieceColor.RED)
            "have a name" in {
                player.name should be("Sum Ting Wong")
            }
            "have a String representation" in {
                player.toString should be("Red: Sum Ting Wong")
            }
        }
    }
}
