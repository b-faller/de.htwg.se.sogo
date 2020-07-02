package de.htwg.se.sogo.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import scala.io.Source._
import de.htwg.se.sogo.controller.controllerComponent._

class SwingGui(controller: ControllerInterface) extends Frame{

  listenTo(controller)

  title = "Sogo (not your email client)"

  def highlightpanel = new FlowPanel {
    contents += new Label("Highlight:")
    
  }

  contents = new BorderPanel {
    add(highlightpanel, BorderPanel.Position.North)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
    contents += new MenuItem(Action("New") { /*TODO: here be actions*/ })
      contents += new MenuItem(Action("Random") { /*TODO: here be actions*/ })
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
            contents += new MenuItem(Action("Undo") { controller.undo })
            contents += new MenuItem(Action("Redo") { controller.redo })
    }
    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Size 3^3") { /*TODO: controller be doing useful things*/ })
      contents += new MenuItem(Action("Size 4^3") { /*TODO: controller be doing useful things*/ })
      contents += new MenuItem(Action("Size 5^3") { /*TODO: controller be doing useful things*/ })

    }
  }

  visible = true
  redraw

  /*
  reactions += {
    case event: GridSizeChanged => resize(event.newSize)
    case event: CellChanged     => redraw
    case event: CandidatesChanged => redraw
  }
*/

  def resize(gridSize: Int) = {
    /* TODO: usefull stuff related to resizing the gameBoard
    cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)
    contents = new BorderPanel {
      add(highlightpanel, BorderPanel.Position.North)
      add(gridPanel, BorderPanel.Position.Center)
      add(statusline, BorderPanel.Position.South)
    }
    */
  }
  def redraw = {
    // TODO: here be actions to perform during redrawing
    repaint
  }
}