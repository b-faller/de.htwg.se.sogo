package de.htwg.se.sogo.aview

import de.htwg.se.sogo.controller.controllerComponent.{
  ControllerInterface,
  GameStatus
}
import de.htwg.se.sogo.util.Observer

class Tui(controller: ControllerInterface) extends Observer {
  controller.add(this)
  val size = 4

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "n" => controller.createNewGameBoard(size)
      case "u" => controller.undo
      case "r" => controller.redo
      case _ =>
        try {
          input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
            case x :: y :: Nil =>
              controller.put(x, y).getOrElse(println("invalid put!"))
            case _ =>
          }
        } catch {
          case _: NumberFormatException =>
        }
    }
  }

  override def update: Unit = {
    println("Game board:")
    println(controller.gameBoardToString)
    println(controller.statusText)
  }
}
