package de.htwg.se.sogo.model.fileIOComponent.fileIOJsonImpl

import com.google.inject.Guice
import java.io._
import play.api.libs.json.{JsNumber, JsValue, Json, Writes}

import de.htwg.se.sogo.model.fileIOComponent.FileIOInterface
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.controller.controllerComponent.GameStatus._

class FileIO extends FileIOInterface{
    def toJson(board: GameBoardInterface, status: GameStatus) = {
        null
    }
    override def save(board: GameBoardInterface, status: GameStatus): Unit = {
        val writer = new PrintWriter(new File("sogoGame.json"))
        writer.write(Json.prettyPrint(toJson(board, status)))
        writer.close()
    }
    override def load(): (GameBoardInterface, GameStatus) = {
        var board: GameBoardInterface = null
        val source: String = Source.fromFile("sogoGame.json").getLines().mkString
        val json: JsValue = Json.parse(source)
        val injector = Guice.createInjector(new SogoModule)
        board = injector.getInstance(classOf[GameBoardInterface])
        null
    }
}