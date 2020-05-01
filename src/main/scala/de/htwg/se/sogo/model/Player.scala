package de.htwg.se.sogo.model

case class Player(name: String, color: GamePieceColor.Value) {
    override def toString: String =
        color.toString.toLowerCase.capitalize + ": " + name
}
