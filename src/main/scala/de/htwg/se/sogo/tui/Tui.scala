package de.htwg.se.sogo.aview

import de.htwg.se.sogo.controller.Controller
import de.htwg.se.sogo.model.{GameBoard}
import de.htwg.se.sogo.util.Observer

class Tui(controller: Controller) extends Observer {
  controller.add(this)
  val size = 4

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "n" => controller.createEmptyGameBoard(size)
      case _ =>
        try {
          input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
            case x :: y :: Nil => controller.put(x, y)
            case _             =>
          }
        } catch {
          case _: NumberFormatException =>
        }
    }
  }

  override def update: Unit = {
    println("Game board:")
    println(controller.gameBoardToString)
    val winner = controller.hasWon()
    winner match {
      case Some(p) => println(p.name + " has won!")
      case None =>
        println(controller.getCurrentPlayer.name + ", please make an input.")
    }
  }
}
