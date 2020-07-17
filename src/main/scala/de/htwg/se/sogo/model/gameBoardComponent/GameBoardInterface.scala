package de.htwg.se.sogo.model.gameBoardComponent

import scala.util.Try

import de.htwg.se.sogo.model.{GamePiece, GamePieceColor}

trait GameBoardInterface {
  def dim: Int
  def placePiece(piece: GamePiece, pos: (Int, Int)): Try[GameBoardInterface]
  def set(piece: Option[GamePiece], pos: (Int, Int, Int)): GameBoardInterface
  def popPiece(pos: (Int, Int)): (GameBoardInterface, Option[GamePiece])
  def get(pos: (Int, Int, Int)): Option[GamePiece]
  def hasWon(pieceColor: GamePieceColor.Value): Boolean
}
