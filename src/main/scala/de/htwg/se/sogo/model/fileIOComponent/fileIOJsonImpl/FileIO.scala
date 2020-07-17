package de.htwg.se.sogo.model.fileIOComponent.fileIOJsonImpl

import scala.util.{Try, Success}
import java.io._
import scala.io.Source
import play.api.libs.json.{JsString, JsNumber, JsValue, Json, Writes}
import play.api.libs.functional.syntax._

import com.google.inject.Inject
import com.google.inject.name.Names
import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._

import de.htwg.se.sogo.model.fileIOComponent.FileIOInterface
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.{GamePiece, GamePieceColor, GameStatus}
import de.htwg.se.sogo.model.GameStatus._
import de.htwg.se.sogo.SogoModule

class FileIO @Inject() (var gameBoard: GameBoardInterface)
    extends FileIOInterface {
  def toJson(board: GameBoardInterface, status: GameStatus) = {
    Json.obj(
      "currentState" -> JsString(status.toString),
      "boardSize" -> JsNumber(board.dim),
      "gameBoard" -> Json.toJson(
        for {
          x <- 0 until board.dim
          y <- 0 until board.dim
          z <- 0 until board.dim
        } yield {
          var obj = Json.obj(
            "x" -> x,
            "y" -> y,
            "z" -> z
          )
          if (!board.get(x, y, z).isEmpty) {
            obj = obj.++(
              Json.obj(
                "color" -> board.get(x, y, z).get.toString
              )
            )
          }
          obj
        }
      )
    )
  }
  override def save(board: GameBoardInterface, status: GameStatus) = {
    val writer = new PrintWriter(new File("sogoGame.json"))
    writer.write(Json.prettyPrint(toJson(board, status)))
    writer.close()
  }

  override def load(): Try[(GameBoardInterface, GameStatus)] = {
    val injector = Guice.createInjector(new SogoModule)
    val source: String = Source.fromFile("sogoGame.json").getLines().mkString
    val json: JsValue = Json.parse(source)
    val stateName = (json \ "currentState").as[String]
    val boardSize = (json \ "boardSize").as[Int]
    val currentState = GameStatus.withName(stateName)

    var board: GameBoardInterface = boardSize match {
      case 2 => injector.instance[GameBoardInterface](Names.named("test"))
      case 3 => injector.instance[GameBoardInterface](Names.named("small"))
      case 4 => injector.instance[GameBoardInterface](Names.named("standard"))
    }

    for (index <- 0 until boardSize * boardSize * boardSize) {
      val cell = (json \ "gameBoard")(index)
      val x = (cell \ "x").as[Int]
      val y = (cell \ "y").as[Int]
      val z = (cell \ "z").as[Int]
      val color = (cell \ "color").asOpt[String]
      if (!color.isEmpty) {
        var piece = new GamePiece(GamePieceColor.withName(color.get))
        board = board.set(Some(piece), (x, y, z))
      }
    }
    Success((board, currentState))
  }
}
