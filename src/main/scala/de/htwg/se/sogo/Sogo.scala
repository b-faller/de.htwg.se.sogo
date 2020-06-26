package de.htwg.se.sogo

import de.htwg.se.sogo.model.gameBoardComponent.gameBoardBaseImpl.GameBoard
import de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.sogo.aview.Tui

object Sogo {
  val defaultsize = 4;
  val controller = new Controller(new GameBoard(defaultsize))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input = ""
    if (!args.isEmpty) {
      tui.processInputLine(args(0))
    } else {
      do {
        input = scala.io.StdIn.readLine()
        tui.processInputLine(input)
      } while (input != "q")
    }
  }
}
