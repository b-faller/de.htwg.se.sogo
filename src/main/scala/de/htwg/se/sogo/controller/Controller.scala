package de.htwg.se.sogo.controller

import de.htwg.se.sogo.controller.GameStatus._
import scala.util.Try

import de.htwg.se.sogo.model.{GameBoard, GamePiece, GamePieceColor, Player}
import de.htwg.se.sogo.util.{Observable, UndoManager}
import scala.collection.mutable.Stack

class Controller(var gameBoard: GameBoard) extends Observable {

  private val undoManager = new UndoManager
  var gameStatus: GameStatus = RED_TURN
  var stateStackForeward = Stack[GameStatus]()
  var stateStackBackward = Stack[GameStatus]()

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
    println("Controller: Check if Game was won")
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
}
