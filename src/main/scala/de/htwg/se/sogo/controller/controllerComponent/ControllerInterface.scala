package de.htwg.se.sogo.controller.controllerComponent

import scala.util.Try

import de.htwg.se.sogo.model.GamePiece
import de.htwg.se.sogo.model.GameStatus._
import de.htwg.se.sogo.util.Observable
import scala.swing.Publisher
import scala.swing.event.Event
import de.htwg.se.sogo.model.GamePieceColor

trait ControllerInterface extends Publisher {
  def createDefaultGameBoard: Unit
  def createNewGameBoard(size: Int): Unit
  def gameBoardSize: Int
  def put(x: Int, y: Int): Try[Unit]
  def getGamePieceColor(x: Int, y: Int, z: Int): Option[GamePieceColor.Value]
  def undo: Unit
  def redo: Unit
  def gameBoardToString: String
  def gameStatus: GameStatus
  def statusText: String
  def setActiveBoardLayer(z: Int): Unit
  def getActiveBoardLayer: Int
  def load: Unit
  def save: Unit
}

class BoardChanged extends Event
class BoardLayerChanged(var z: Int) extends Event
class BoardContentChanged(var x: Int, var y: Int) extends Event
