package de.htwg.se.sogo.controller

import de.htwg.se.sogo.model.{GameBoard, GamePiece, GamePieceColor, Player}
import de.htwg.se.sogo.util.{Observable, UndoManager}

class Controller(var gameBoard: GameBoard) extends Observable {

  private val undoManager = new UndoManager

  val players = Vector(
    new Player("Player 1", GamePieceColor.RED),
    new Player("Player 2", GamePieceColor.BLUE)
  )
  var currentPlayer = 0

  def getCurrentPlayer: Player = players(currentPlayer)

  def createEmptyGameBoard(size: Int): Unit = {
    gameBoard = new GameBoard(size)
    notifyObservers
  }

  def put(x: Int, y: Int): Unit = {
    val piece = new GamePiece(players(currentPlayer).color)
    undoManager.doStep(new PutCommand(x, y, piece, this))
    currentPlayer = (currentPlayer + 1) % players.length
    notifyObservers
  }

  def get(x: Int, y: Int, z: Int): Option[GamePiece] = {
    gameBoard.get(x, y, z)
  }

  def hasWon(): Option[Player] = {
    for (p <- players) {
      if (gameBoard.hasWon(p.color)) return Some(p)
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
