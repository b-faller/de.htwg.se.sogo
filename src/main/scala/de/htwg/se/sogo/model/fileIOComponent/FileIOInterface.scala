package de.htwg.se.sogo.model.fileIOComponent

import scala.util.Try

import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.GameStatus._

trait FileIOInterface {
  def load: Try[(GameBoardInterface, GameStatus)]
  def save(board: GameBoardInterface, status: GameStatus): Unit
}
