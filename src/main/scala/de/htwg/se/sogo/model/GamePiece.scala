package de.htwg.se.sogo.model

case class GamePiece(color: GamePieceColor.Value) {
    override def toString() = color match {
        case GamePieceColor.RED => "R"
        case GamePieceColor.BLUE => "B"
    }
}