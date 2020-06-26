package de.htwg.se.sogo.model.gameBoardComponent.gameBoardAdvancedImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.gameBoardComponent.gameBoardBaseImpl.{
  GameBoard => BaseGameBoard
}

import scala.collection.immutable

class GameBoard @Inject() (@Named("DefaultSize") size: Int)
    extends BaseGameBoard(size) {}
