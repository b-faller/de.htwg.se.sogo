package de.htwg.se.sogo

import com.google.inject.Guice

import de.htwg.se.sogo.controller.controllerComponent.ControllerInterface
import de.htwg.se.sogo.aview.Tui
import de.htwg.se.sogo.aview.gui.SwingGui

object Sogo {
  val injector = Guice.createInjector(new SogoModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)

  controller.createDefaultGameBoard

  def main(args: Array[String]): Unit = {
    // Init gui only if --nogui is not passed
    if (args.size > 1 && args(1) != "--nogui") {
      val gui = new SwingGui(controller)
    }

    var input = ""
    do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
