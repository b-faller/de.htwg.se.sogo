package de.htwg.se.sogo

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule

import de.htwg.se.sogo.controller.controllerComponent._
import de.htwg.se.sogo.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.sogo.model.gameBoardComponent.gameBoardAdvancedImpl.GameBoard
import de.htwg.se.sogo.model.fileIOComponent._

class SogoModule extends AbstractModule with ScalaModule {
  val defaultSize: Int = 4

  override def configure() = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[GameBoardInterface].to[GameBoard]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
    bind[FileIOInterface].to[fileIOXmlImpl.FileIO]
    bind[GameBoardInterface]
      .annotatedWithName("test")
      .toInstance(new GameBoard(2))
    bind[GameBoardInterface]
      .annotatedWithName("small")
      .toInstance(new GameBoard(3))
    bind[GameBoardInterface]
      .annotatedWithName("standard")
      .toInstance(new GameBoard(4))
  }
}
