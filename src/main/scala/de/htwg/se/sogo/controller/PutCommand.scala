package de.htwg.se.sogo.controller

import de.htwg.se.sogo.controller.GameStatus._
import de.htwg.se.sogo.util.Command
import de.htwg.se.sogo.model.GamePiece
import de.htwg.se.sogo.model.GameBoard
import de.htwg.se.sogo.model.GamePieceColor._

class PutCommand(x: Int, y: Int, piece: GamePiece, controller: Controller)
    extends Command {
  override def doStep: Unit = {
    controller.gameBoard = controller.gameBoard.placePiece(piece, (x, y))
    controller.gameStatus = if (controller.gameStatus == RED_TURN) BLUE_TURN else RED_TURN
    
    controller.gameStatus = controller.hasWon() match{
      case Some(player) => if(player.color.equals(RED)) RED_WON else BLUE_WON
      case None => controller.gameStatus
    }
  }

  override def undoStep: Unit =
    controller.gameBoard = controller.gameBoard.popPiece((x, y))._1

  override def redoStep: Unit =
    controller.gameBoard = controller.gameBoard.placePiece(piece, (x, y))
}
