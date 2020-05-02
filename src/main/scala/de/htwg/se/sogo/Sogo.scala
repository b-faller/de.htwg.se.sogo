package de.htwg.se.sogo

import de.htwg.se.sogo.model.{GameBoard}
import de.htwg.se.sogo.controller.Controller
import de.htwg.se.sogo.aview.Tui

object Sogo {
    val controller = new Controller(new GameBoard(4, 4, 4))
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
