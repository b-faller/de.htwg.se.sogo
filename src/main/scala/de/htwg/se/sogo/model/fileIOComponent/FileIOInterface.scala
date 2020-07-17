package de.htwg.se.sogo.model.fileIOComponent

import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.controller.controllerComponent.GameStatus._

trait FileIOInterface {
    def load: (GameBoardInterface, GameStatus)
    def save(board: GameBoardInterface, status: GameStatus): Boolean
}