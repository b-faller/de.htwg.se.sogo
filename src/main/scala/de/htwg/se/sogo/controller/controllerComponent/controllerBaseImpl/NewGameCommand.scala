package de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.sogo.util.Command
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.gameBoardComponent.gameBoardBaseImpl.GameBoard

class NewGameCommand(size: Int, controller: Controller) extends Command {
  var memento: GameBoardInterface = controller.gameBoard

  override def doStep: Unit = {
    memento = controller.gameBoard
    controller.gameBoard = new GameBoard(size)
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
