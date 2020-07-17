package de.htwg.se.sogo.model.fileIOComponent.fileIOXmlImpl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import de.htwg.se.sogo.model.fileIOComponent.fileIOXmlImpl.FileIO
import de.htwg.se.sogo.model.gameBoardComponent.gameBoardAdvancedImpl.GameBoard
import de.htwg.se.sogo.model.{GameStatus, GamePiece}

class FileIOSpec extends AnyWordSpec with Matchers {
  "A XML file I/O" when {
    "instanciated" should {
      "save and load" in {
        val fileIo = new FileIO()
        val board2x = new GameBoard(2)
        fileIo.save(board2x, GameStatus.BLUE_TURN)
        val (loadBoard2x, loadStatus1) = fileIo.load.get
        loadBoard2x should be(board2x)
        loadStatus1 should be(GameStatus.BLUE_TURN)

        val board3x = new GameBoard(3)
        fileIo.save(board3x, GameStatus.BLUE_TURN)
        val (loadBoard3x, loadStatus2) = fileIo.load.get
        loadBoard3x should be(board3x)
        loadStatus2 should be(GameStatus.BLUE_TURN)
      }
    }
  }
}
