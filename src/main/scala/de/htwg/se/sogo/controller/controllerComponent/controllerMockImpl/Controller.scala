package de.htwg.se.sogo.controller.controllerComponent.controllerMockImpl

import scala.util.Try

import de.htwg.se.sogo.controller.controllerComponent.GameStatus._
import de.htwg.se.sogo.controller.controllerComponent._
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.playerComponent.Player
import de.htwg.se.sogo.model.GamePieceColor
import de.htwg.se.sogo.model.fileIOComponent.FileIOInterface

class Controller(var gameBoard: GameBoardInterface, var fileIO: FileIOInterface)
    extends ControllerInterface {

  var gameStatus: GameStatus = RED_TURN

  def createDefaultGameBoard: Unit = {}

  def createNewGameBoard(size: Int): Unit = {}

  def gameBoardSize: Int = 4

  def put(x: Int, y: Int): Try[Unit] = Try((): Unit)

  def getGamePieceColor(x: Int, y: Int, z: Int): Option[GamePieceColor.Value] = None

  def hasWon(): Option[Player] = None

  def undo: Unit = {}

  def redo: Unit = {}

  def gameBoardToString(): String = gameBoard.toString

  def statusText: String = GameStatus.message(gameStatus)

  def setActiveBoardLayer(z: Int): Unit = {}

  def getActiveBoardLayer: Int = 0

  def save: Boolean = false

  def load: Boolean = false
}
