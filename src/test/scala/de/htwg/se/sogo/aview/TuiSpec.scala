package de.htwg.se.sogo.aview

import de.htwg.se.sogo.controller.Controller
import de.htwg.se.sogo.model.{GameBoard, GamePiece, GamePieceColor}

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TuiSpec extends AnyWordSpec with Matchers {
    "A Sogo Tui" should {
        val controller = new Controller(new GameBoard(4, 4, 4))
        val tui = new Tui(controller)
        "create an empty Sogo on input 'n'" in {
            tui.processInputLine("n")
            val newGameBoard = new GameBoard(4, 4, 4)
            controller.gameBoard should be(newGameBoard)
        }
        "put a GamePiece on input '23'" in {
            tui.processInputLine("23")
            controller.gameBoard.retrievePiece(2, 3, 0) should be(
                Some(GamePiece(GamePieceColor.RED))
            )
        }
        "ignore an malformed put instruction" in {
            tui.processInputLine("222")
            // TODO: ingore values such as 99
        }
        "ignore an arbitrary input" in {
            tui.processInputLine("something went wrong")
        }
    }
}
