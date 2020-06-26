package de.htwg.se.sogo.model.gameBoardComponent

import scala.util.Try

import de.htwg.se.sogo.model.{GamePiece, GamePieceColor}

trait GameBoardInterface {
  def placePiece(piece: GamePiece, pos: (Int, Int)): Try[GameBoardInterface]
  def popPiece(pos: (Int, Int)): (GameBoardInterface, Option[GamePiece])
  def get(pos: (Int, Int, Int)): Option[GamePiece]
  def hasWon(pieceColor: GamePieceColor.Value): Boolean
}
