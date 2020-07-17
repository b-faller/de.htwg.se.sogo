package de.htwg.se.sogo.model.fileIOComponent.fileIOXmlImpl

import java.io._

import scala.util.{Try, Success}
import scala.xml.{NodeSeq, PrettyPrinter}

import com.google.inject.Inject
import com.google.inject.name.Names
import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._

import de.htwg.se.sogo.model.fileIOComponent.FileIOInterface
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.{GamePiece, GamePieceColor, GameStatus}
import de.htwg.se.sogo.model.GameStatus._
import de.htwg.se.sogo.SogoModule

class FileIO extends FileIOInterface {
  def toXml(board: GameBoardInterface, status: GameStatus) = {
    <game>
      <currentState>{status.toString}</currentState>
      <boardSize>{board.dim}</boardSize>
      <gameBoard>
      {
        for {
          x <- 0 until board.dim
          y <- 0 until board.dim
          z <- 0 until board.dim
        } yield {
          <place>
            <x>{x}</x>
            <y>{y}</y>
            <z>{z}</z>
            {
              if (!board.get(x, y, z).isEmpty) {
                <color>{board.get(x, y, z).get.color}</color>
              }
            }
          </place>
        }
      }
      </gameBoard>
    </game>
  }

  override def save(board: GameBoardInterface, status: GameStatus) = {
    val pp = new scala.xml.PrettyPrinter(80, 2)
    val xml = pp.format(toXml(board, status))
    val file = new File("sogoGame.xml")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(xml)
    bw.close()
  }

  override def load(): Try[(GameBoardInterface, GameStatus)] = {
    val injector = Guice.createInjector(new SogoModule)
    var xml = scala.xml.XML.loadFile("sogoGame.xml")

    val stateName = (xml \ "currentState").text
    val currentState = GameStatus.withName(stateName)

    val boardSize = (xml \ "boardSize").text.toInt
    var board: GameBoardInterface = boardSize match {
      case 2 => injector.instance[GameBoardInterface](Names.named("test"))
      case 3 => injector.instance[GameBoardInterface](Names.named("small"))
      case 4 => injector.instance[GameBoardInterface](Names.named("standard"))
    }

    for (place <- (xml \\ "place")) {
      val x = (place \ "x").text.toInt
      val y = (place \ "y").text.toInt
      val z = (place \ "z").text.toInt
      val color = (place \ "color")
      if (!color.isEmpty) {
        var piece = new GamePiece(GamePieceColor.withName(color.text))
        board = board.set(Some(piece), (x, y, z))
      }
    }
    Success((board, currentState))
  }
}
