package de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._

import de.htwg.se.sogo.SogoModule
import de.htwg.se.sogo.util.Command
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface

class NewGameCommand(size: Int, controller: Controller) extends Command {
  var memento: GameBoardInterface = controller.gameBoard

  override def doStep: Unit = {
    memento = controller.gameBoard
    val injector = Guice.createInjector(new SogoModule)
    size match {
      case 2 => {
        controller.gameBoard =
          injector.instance[GameBoardInterface](Names.named("test"))
      }
      case 3 => {
        controller.gameBoard =
          injector.instance[GameBoardInterface](Names.named("small"))
      }
      case 4 => {
        controller.gameBoard =
          injector.instance[GameBoardInterface](Names.named("standard"))
      }
    }
  }

  override def undoStep: Unit = {
    val new_memento = controller.gameBoard
    controller.gameBoard = memento
    memento = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = controller.gameBoard
    controller.gameBoard = memento
    memento = new_memento
  }
}
