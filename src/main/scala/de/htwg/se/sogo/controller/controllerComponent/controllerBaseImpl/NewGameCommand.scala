package de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._

import de.htwg.se.sogo.SogoModule
import de.htwg.se.sogo.util.Command
import de.htwg.se.sogo.model.GameStatus._
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface

class NewGameCommand(size: Int, controller: Controller) extends Command {
  var memento_gb: GameBoardInterface = controller.gameBoard
  var memento_state: GameStatus = controller.gameStatus

  override def doStep: Unit = {
    memento_gb = controller.gameBoard
    memento_state = controller.gameStatus
    val injector = Guice.createInjector(new SogoModule)
    controller.gameBoard = size match {
      case 2 => injector.instance[GameBoardInterface](Names.named("test"))
      case 3 => injector.instance[GameBoardInterface](Names.named("small"))
      case 4 => injector.instance[GameBoardInterface](Names.named("standard"))
    }
    controller.gameStatus = RED_TURN
  }

  override def undoStep: Unit = {
    val new_memento_gb = controller.gameBoard
    val new_memento_state = controller.gameStatus
    controller.gameBoard = memento_gb
    controller.gameStatus = memento_state
    memento_gb = new_memento_gb
    memento_state = new_memento_state
  }

  override def redoStep: Unit = {
    val new_memento_gb = controller.gameBoard
    val new_memento_state = controller.gameStatus
    controller.gameBoard = memento_gb
    controller.gameStatus = memento_state
    memento_gb = new_memento_gb
    memento_state = new_memento_state
  }
}
