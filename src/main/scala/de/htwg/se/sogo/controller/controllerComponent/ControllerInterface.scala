package de.htwg.se.sogo.controller.controllerComponent

import scala.util.Try

import de.htwg.se.sogo.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.sogo.util.Observable
import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher {
  def createDefaultGameBoard: Unit
  def createNewGameBoard(size: Int): Unit
  def gameBoardSize: Int
  def put(x: Int, y: Int): Try[Unit]
  def get(x: Int, y: Int, z: Int): Option[GamePiece]
  def undo: Unit
  def redo: Unit
  def gameBoardToString: String
  def gameStatus: GameStatus
  def statusText: String
}

class BoardContentChanged extends Event
class BoardChanged extends Event
