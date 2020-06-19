package de.htwg.se.sogo.aview

import scala.language.reflectiveCalls

import de.htwg.se.sogo.controller.Controller
import de.htwg.se.sogo.model.{GameBoard, GamePiece, GamePieceColor}

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TuiSpec extends AnyWordSpec with Matchers {

  def fixture = new {
    val controller = new Controller(new GameBoard(4, 4, 4))
    val tui = new Tui(controller)
  }

  "A Sogo Tui" should {
    "create an empty Sogo on input 'n'" in {
      val f = fixture
      f.tui.processInputLine("n")
      val newGameBoard = new GameBoard(4, 4, 4)
      f.controller.gameBoard should be(newGameBoard)
    }
    "undo a step on input 'u'" in {
      val f = fixture
      f.tui.processInputLine("00")
      f.tui.processInputLine("u")
      f.controller.gameBoard.get((0, 0, 0)) should be(None)
    }
    "redo a step on input 'r'" in {
      val f = fixture
      f.tui.processInputLine("00")
      f.tui.processInputLine("u")
      f.tui.processInputLine("r")
      f.controller.gameBoard.get((0, 0, 0)) should be(
        Some(GamePiece(GamePieceColor.RED))
      )
    }
    "put a GamePiece on input '23'" in {
      val f = fixture
      f.tui.processInputLine("23")
      f.controller.gameBoard.get(2, 3, 0) should be(
        Some(GamePiece(GamePieceColor.RED))
      )
    }
    "ignore an malformed put instruction" in {
      val f = fixture
      f.tui.processInputLine("222")
      // TODO: ingore values such as 99
    }
    "ignore an arbitrary input" in {
      val f = fixture
      f.tui.processInputLine("something went wrong")
    }
  }
}
