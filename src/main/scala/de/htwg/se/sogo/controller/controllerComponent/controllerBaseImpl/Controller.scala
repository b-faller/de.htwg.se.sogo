package de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl

import scala.util.Try

import de.htwg.se.sogo.controller.controllerComponent.GameStatus._
import de.htwg.se.sogo.controller.controllerComponent._
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.{GamePiece, GamePieceColor}
import de.htwg.se.sogo.model.playerComponent.Player
import de.htwg.se.sogo.util.{Observable, UndoManager}

class Controller(var gameBoard: GameBoardInterface)
    extends ControllerInterface {

  private val undoManager = new UndoManager
  var gameStatus: GameStatus = RED_TURN

  val players = Vector(
    new Player("Player 1", GamePieceColor.RED),
    new Player("Player 2", GamePieceColor.BLUE)
  )

  def createEmptyGameBoard(size: Int): Unit = {
    undoManager.doStep(new NewGameCommand(size, this)).get
    notifyObservers
  }

  def put(x: Int, y: Int): Try[Unit] = {
    val piece = new GamePiece(players(GameStatus.player(this.gameStatus)).color)
    val result = undoManager.doStep(new PutCommand(x, y, piece, this))
    notifyObservers
    result
  }

  def get(x: Int, y: Int, z: Int): Option[GamePiece] = {
    gameBoard.get(x, y, z)
  }

  def hasWon(): Option[Player] = {
    for (p <- players) {
      if (gameBoard.hasWon(p.color)) {
        return Some(p)
      }

    }
    None
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }

  def gameBoardToString(): String = gameBoard.toString

  def statusText: String = GameStatus.message(gameStatus)
}
