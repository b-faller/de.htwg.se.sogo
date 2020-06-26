package de.htwg.se.sogo.model.playerComponent

import de.htwg.se.sogo.model.GamePieceColor

case class Player(name: String, color: GamePieceColor.Value) {
  override def toString: String =
    color.toString.toLowerCase.capitalize + ": " + name
}
