package de.htwg.se.sogo.controller

import de.htwg.se.sogo.util.Command
import de.htwg.se.sogo.model.GamePiece

class PutCommand(x: Int, y: Int, piece: GamePiece, controller: Controller)
    extends Command {
  override def doStep: Unit =
    controller.gameBoard = controller.gameBoard.placePiece(piece, (x, y)).get

  override def undoStep: Unit =
    controller.gameBoard = controller.gameBoard.popPiece((x, y))._1

  override def redoStep: Unit =
    controller.gameBoard = controller.gameBoard.placePiece(piece, (x, y)).get
}
