package de.htwg.se.sogo.controller.controllerComponent.controllerMockImpl

import scala.util.Try

import de.htwg.se.sogo.controller.controllerComponent.GameStatus._
import de.htwg.se.sogo.controller.controllerComponent._
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.GamePiece
import de.htwg.se.sogo.model.playerComponent.Player

class Controller(var gameBoard: GameBoardInterface)
    extends ControllerInterface {

  var gameStatus: GameStatus = RED_TURN

  def createDefaultGameBoard: Unit = {}

  def createNewGameBoard(size: Int): Unit = {}

  def put(x: Int, y: Int): Try[Unit] = Try((): Unit)

  def get(x: Int, y: Int, z: Int): Option[GamePiece] = gameBoard.get((x, y, z))

  def hasWon(): Option[Player] = None

  def undo: Unit = {}

  def redo: Unit = {}

  def gameBoardToString(): String = gameBoard.toString

  def statusText: String = GameStatus.message(gameStatus)
}
