package de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl

import scala.language.reflectiveCalls

import de.htwg.se.sogo.controller.controllerComponent.GameStatus._
import de.htwg.se.sogo.controller.controllerComponent.GameStatus
import de.htwg.se.sogo.model.gameBoardComponent.gameBoardBaseImpl.GameBoard
import de.htwg.se.sogo.model.{GamePiece, GamePieceColor}
import de.htwg.se.sogo.model.playerComponent.Player
import de.htwg.se.sogo.util.Observer

import org.scalatest.wordspec.{AnyWordSpec}
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyWordSpec with Matchers {

  def fixture = new {
    val gameBoard = new GameBoard(4)
    val controller = new Controller(gameBoard)
    /*
    val observer = new Observer {
      var updated: Boolean = false
      def isUpdated: Boolean = updated
      override def update: Unit = { this.updated = true }
    }
    controller.add(observer)*/
  }

  "A Controller" when {
    /*
    "observed by an observer" should {
      "notify its Observer after creation" in {
        val f = fixture
        f.observer.isUpdated should be(false)
        var newGameBoard = new GameBoard(3)
        f.controller.createNewGameBoard(3)
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
    */
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
      "create a test game board" in {
        val f = fixture
        f.controller.gameBoard.dim should be(4)
        f.controller.createNewGameBoard(2)
        var newGameBoard = new GameBoard(2)
        f.controller.gameBoard should be(newGameBoard)
        f.controller.gameBoard.dim should be(2)
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
        var gameBoard = new GameBoard(2)
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
        controller.gameBoard.get((0, 0, 0)) should be(None)
        controller.put(0, 0)
        controller.gameBoard.get((0, 0, 0)) should be(red)
        controller.undo
        controller.gameBoard.get((0, 0, 0)) should be(None)
      }
      "redo the last put" in {
        var controller = new Controller(new GameBoard(2))
        val red = Some(GamePiece(GamePieceColor.RED))
        controller.gameBoard.get((0, 0, 0)) should be(None)
        controller.put(0, 0)
        controller.undo
        controller.redo
        controller.gameBoard.get((0, 0, 0)) should be(red)
      }
      "undo starting a new game" in {
        val oldGameBoard = new GameBoard(2)
        val newGameBoard = new GameBoard(3)
        var controller = new Controller(oldGameBoard)
        controller.gameBoard should be(oldGameBoard)
        controller.createNewGameBoard(3)
        controller.gameBoard should be(newGameBoard)
        controller.undo
        controller.gameBoard should be(oldGameBoard)
      }
      "redo starting a new game" in {
        val oldGameBoard = new GameBoard(2)
        val newGameBoard = new GameBoard(3)
        var controller = new Controller(oldGameBoard)
        controller.gameBoard should be(oldGameBoard)
        controller.createNewGameBoard(3)
        controller.undo
        controller.redo
        controller.gameBoard should be(newGameBoard)
      }
      "undo/redo the hasWon status" in {
        val controller = new Controller(new GameBoard(3))
        controller.gameStatus should be(GameStatus.RED_TURN)
        controller.put(0, 0)
        controller.put(1, 0)
        controller.put(0, 0)
        controller.put(1, 0)
        controller.put(0, 0)
        controller.gameStatus should be(GameStatus.RED_WON)
        controller.undo
        controller.gameStatus should be(GameStatus.RED_TURN)
        controller.redo
        controller.gameStatus should be(GameStatus.RED_WON)
        controller.undo
        controller.put(2, 2)
        controller.put(1, 0)
        controller.gameStatus should be(GameStatus.BLUE_WON)
        controller.undo
        controller.gameStatus should be(GameStatus.BLUE_TURN)
        controller.redo
        controller.gameStatus should be(GameStatus.BLUE_WON)
      }
      "not fail to undo after an invalid put" in {
        var controller = new Controller(new GameBoard(2))
        controller.put(9, 9)
        controller.undo
      }
      "not process put commands after a player has won" in {
        val controller = new Controller(new GameBoard(2))
        controller.put(0, 0)
        controller.put(0, 1)
        controller.put(0, 0)
        controller.put(1, 1)
        controller.gameStatus should be(GameStatus.RED_WON)
        controller.get(1, 1, 0) should be(None)
      }
    }
  }
}
