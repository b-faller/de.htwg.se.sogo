package de.htwg.se.sogo.controller

import scala.language.reflectiveCalls

import de.htwg.se.sogo.model.{GameBoard, GamePiece, GamePieceColor, Player}
import de.htwg.se.sogo.util.Observer

import org.scalatest.wordspec.{AnyWordSpec}
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyWordSpec with Matchers {

  def fixture = new {
    val gameBoard = new GameBoard(4, 4, 4)
    val controller = new Controller(gameBoard)
    val observer = new Observer {
      var updated: Boolean = false
      def isUpdated: Boolean = updated
      override def update: Unit = { this.updated = true }
    }
    controller.add(observer)
  }

  "A Controller" when {
    "observed by an observer" should {
      "notify its Observer after creation" in {
        val f = fixture
        f.observer.isUpdated should be(false)
        val newGameBoard = new GameBoard(3, 3, 3)
        f.controller.createEmptyGameBoard(3)
        f.controller.gameBoard should be(newGameBoard)
        f.observer.isUpdated should be(true)
      }
      "notify its Observer after GamePiece placement" in {
        val f = fixture
        f.observer.isUpdated should be(false)
        f.controller.put(1, 1)
        f.observer.isUpdated should be(true)
      }
      "not notify its Observer after a get command" in {
        val f = fixture
        f.controller.get(0, 0, 0)
        f.observer.isUpdated should be(false)
      }
    }
    "controlling" should {
      val p1 = new Player("Player 1", GamePieceColor.RED)
      val p2 = new Player("Player 2", GamePieceColor.BLUE)
      "have two players" in {
        val f = fixture
        f.controller.players.length should be(2)
      }
      "initialize the players" in {
        val f = fixture
        f.controller.players(0) should be(p1)
        f.controller.players(1) should be(p2)
      }
      "change state after placing a GamePiece" in {
        val f = fixture
        f.controller.gameStatus should be(GameStatus.RED_TURN)
        f.controller.put(1, 1)
        f.controller.gameStatus should be(GameStatus.BLUE_TURN)
        f.controller.put(1, 2)
        f.controller.gameStatus should be(GameStatus.RED_TURN)
      }
      "place a GamePiece with the players color" in {
        val f = fixture
        val red = Some(GamePiece(GamePieceColor.RED))
        val blue = Some(GamePiece(GamePieceColor.BLUE))
        f.controller.gameStatus = GameStatus.RED_TURN
        f.controller.put(1, 1)
        f.controller.get(1, 1, 0) should be(red)

        f.controller.gameStatus = GameStatus.BLUE_TURN
        f.controller.put(1, 2)
        f.controller.get(1, 2, 0) should be(blue)
      }
      "return wether a player has won" in {
        var gameBoard = new GameBoard(2, 2, 2)
        var controller = new Controller(gameBoard)
        val red = Some(GamePiece(GamePieceColor.RED))
        val p1 = new Player("Player 1", GamePieceColor.RED)
        controller.hasWon should be(None)

        gameBoard = gameBoard.set(red, (0, 0, 0))
        gameBoard = gameBoard.set(red, (1, 0, 0))
        controller = new Controller(gameBoard)
        controller.hasWon should be(Some(p1))
      }
      "undo the last put" in {
        var controller = new Controller(new GameBoard(2))
        val red = Some(GamePiece(GamePieceColor.RED))
        controller.gameBoard.get((0,0,0)) should be(None)
        controller.put(0, 0)
        controller.gameBoard.get((0,0,0)) should be(red)
        controller.undo
        controller.gameBoard.get((0,0,0)) should be(None)
      }
      "redo the last put" in {
        var controller = new Controller(new GameBoard(2))
        val red = Some(GamePiece(GamePieceColor.RED))
        controller.gameBoard.get((0,0,0)) should be(None)
        controller.put(0, 0)
        controller.undo
        controller.redo
        controller.gameBoard.get((0,0,0)) should be(red)
      }
      "undo starting a new game" in {
        val oldGameBoard = new GameBoard(2)
        val newGameBoard = new GameBoard(3)
        var controller = new Controller(oldGameBoard)
        controller.gameBoard should be(oldGameBoard)
        controller.createEmptyGameBoard(3)
        controller.gameBoard should be(newGameBoard)
        controller.undo
        controller.gameBoard should be(oldGameBoard)
      }
      "redo starting a new game" in {
        val oldGameBoard = new GameBoard(2)
        val newGameBoard = new GameBoard(3)
        var controller = new Controller(oldGameBoard)
        controller.gameBoard should be(oldGameBoard)
        controller.createEmptyGameBoard(3)
        controller.undo
        controller.redo
        controller.gameBoard should be(newGameBoard)
      }
    }
  }
}
