package de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl

import scala.util.Try

import com.google.inject.Inject

import de.htwg.se.sogo.controller.controllerComponent.GameStatus._
import de.htwg.se.sogo.controller.controllerComponent._
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.{GamePiece, GamePieceColor}
import de.htwg.se.sogo.model.playerComponent.Player
import de.htwg.se.sogo.util.{Observable, UndoManager}
import scala.swing.Publisher

class Controller @Inject() (var gameBoard: GameBoardInterface)
    extends ControllerInterface with Publisher{

  private val undoManager = new UndoManager
  var gameStatus: GameStatus = RED_TURN

  val players = Vector(
    new Player("Player 1", GamePieceColor.RED),
    new Player("Player 2", GamePieceColor.BLUE)
  )

  def createDefaultGameBoard: Unit = {
    createNewGameBoard(4)
  }

  def createNewGameBoard(size: Int): Unit = {
    undoManager.doStep(new NewGameCommand(size, this)).get
    publish(new BoardChanged)
  }

  def gameBoardSize: Int = gameBoard.dim

  def put(x: Int, y: Int): Try[Unit] = {
    val piece = new GamePiece(players(GameStatus.player(this.gameStatus)).color)
    val result = undoManager.doStep(new PutCommand(x, y, piece, this))
    publish(new BoardContentChanged)
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
    publish(new BoardContentChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new BoardContentChanged)
  }

  def gameBoardToString(): String = gameBoard.toString

  def statusText: String = GameStatus.message(gameStatus)
}
