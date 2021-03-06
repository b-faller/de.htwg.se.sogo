package de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl

import scala.util.Try

import com.google.inject.{Guice, Inject}

import de.htwg.se.sogo.controller.controllerComponent._
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.{GamePieceColor, GamePiece, GameStatus}
import de.htwg.se.sogo.model.GameStatus._
import de.htwg.se.sogo.model.playerComponent.Player
import de.htwg.se.sogo.util.{Observable, UndoManager}
import de.htwg.se.sogo.model.fileIOComponent.FileIOInterface
import de.htwg.se.sogo.SogoModule
import scala.swing.Publisher

class Controller @Inject() (
    var gameBoard: GameBoardInterface,
    var fileIO: FileIOInterface
) extends ControllerInterface
    with Publisher {

  private val undoManager = new UndoManager
  var gameStatus: GameStatus = RED_TURN
  var activeBoardLayer = 0

  val players = Vector(
    new Player("Player 1", GamePieceColor.RED),
    new Player("Player 2", GamePieceColor.BLUE)
  )

  def createDefaultGameBoard: Unit = {
    createNewGameBoard(4)
    publish(new BoardChanged)
  }

  def createNewGameBoard(size: Int): Unit = {
    undoManager.doStep(new NewGameCommand(size, this)).get
    publish(new BoardChanged)
    setActiveBoardLayer(0)
  }

  def gameBoardSize: Int = gameBoard.dim

  def put(x: Int, y: Int): Try[Unit] = {
    val piece = new GamePiece(players(GameStatus.player(this.gameStatus)).color)
    val result = undoManager.doStep(new PutCommand(x, y, piece, this))
    publish(new BoardContentChanged(x, y))
    result
  }

  def setActiveBoardLayer(z: Int): Unit = {
    activeBoardLayer = z
    publish(new BoardLayerChanged(activeBoardLayer))
  }

  def getActiveBoardLayer: Int = activeBoardLayer

  def getGamePieceColor(
      x: Int,
      y: Int,
      z: Int
  ): Option[GamePieceColor.Value] = {
    val piece = gameBoard.get(x, y, z)
    if (piece.isEmpty) {
      None
    } else {
      Some(piece.get.color)
    }
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
    publish(new BoardChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new BoardChanged)
  }

  override def save: Unit = fileIO.save(gameBoard, gameStatus)

  override def load = {
    val (board: GameBoardInterface, status: GameStatus) = fileIO.load.get
    gameBoard = board
    gameStatus = status
    undoManager.clear
    publish(new BoardChanged)
  }

  def gameBoardToString(): String = gameBoard.toString

  def statusText: String = GameStatus.message(gameStatus)
}
