package de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.sogo.controller.controllerComponent.GameStatus._
import de.htwg.se.sogo.util.Command
import de.htwg.se.sogo.model.GamePiece
import de.htwg.se.sogo.model.GamePieceColor._

class PutCommand(x: Int, y: Int, piece: GamePiece, controller: Controller)
    extends Command {
  var memento: GameStatus = controller.gameStatus

  override def doStep: Unit = {
    if (controller.gameStatus == RED_TURN
        || controller.gameStatus == BLUE_TURN) {

      memento = controller.gameStatus
      controller.gameBoard = controller.gameBoard.placePiece(piece, (x, y)).get

      controller.gameStatus =
        if (controller.gameStatus == RED_TURN) BLUE_TURN else RED_TURN

      controller.gameStatus = controller.hasWon() match {
        case Some(player) => if (player.color.equals(RED)) RED_WON else BLUE_WON
        case None         => controller.gameStatus
      }
    }
  }

  override def undoStep: Unit = {
    val new_memento = controller.gameStatus
    controller.gameBoard = controller.gameBoard.popPiece((x, y))._1
    controller.gameStatus = memento
    memento = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = controller.gameStatus
    controller.gameBoard = controller.gameBoard.placePiece(piece, (x, y)).get
    controller.gameStatus = memento
    memento = new_memento
  }
}
