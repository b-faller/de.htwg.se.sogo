package de.htwg.se.sogo.controller.controllerComponent

import scala.util.Try

import de.htwg.se.sogo.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.sogo.util.Observable
import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher {
  def createDefaultGameBoard: Unit
  def createNewGameBoard(size: Int): Unit
  def put(x: Int, y: Int): Try[Unit]
  def undo: Unit
  def redo: Unit
  def gameBoardToString: String
  def gameStatus: GameStatus
  def statusText: String
}

class boardContentChanged extends Event
class boardChanged extends Event
