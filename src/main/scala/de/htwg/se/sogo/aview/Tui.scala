package de.htwg.se.sogo.aview

import de.htwg.se.sogo.controller.controllerComponent.{
  ControllerInterface,
  BoardChanged,
  BoardContentChanged
}
import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor {
  listenTo(controller)

  def processInputLine(input: String): Unit = {
    input match {
      case "q" => System.exit(0)
      case "n" => controller.createDefaultGameBoard
      case "u" => controller.undo
      case "r" => controller.redo
      case "s" => controller.save
      case "l" => controller.load
      case _ => {
        try {
          input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
            case x :: y :: Nil =>
              controller.put(x, y).getOrElse(println("invalid put!"))
            case _ =>
          }
        } catch {
          case _: NumberFormatException =>
          case _: NullPointerException  =>
        }
      }
    }
  }

  reactions += {
    case event: BoardChanged        => printTui
    case event: BoardContentChanged => printTui
  }

  def printTui: Unit = {
    println("Game board:")
    println(controller.gameBoardToString)
    println(controller.statusText)
  }
}
