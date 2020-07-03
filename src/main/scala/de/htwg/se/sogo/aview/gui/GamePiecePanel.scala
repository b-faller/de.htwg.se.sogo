package de.htwg.se.sogo.aview.gui

import scala.swing._
import scala.swing.event._

import de.htwg.se.sogo.controller.controllerComponent.{
  GameBoardContentsChanged,
  ControllerInterface
}

class GamePiecePanel(x: Int, y: Int, z: Int, controller: ControllerInterface)
    extends FlowPanel {

  val noPieceColor = new Color(50, 50, 50)
  val redColor = new Color(255, 0, 0)
  val blueColor = new Color(0, 0, 255)

  def myPiece = controller.get(x, y, z)

  def pieceColor: Color = {
    if (myPiece == "") {
      cellColor
    } else if (myPiece == "R") {
      redColor
    } else if (myPiece == "B") {
      blueColor
    }
  }

  val cell = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(51, 51)
    background = pieceColor
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(controller)
    reactions += {
      case e: GameBoardContentsChanged => {
        background = pieceColor
        repaint
      }
      case MouseClicked(src, pt, mod, clicks, pops) => {
        controller.put(x, y)
        repaint
      }
    }
  }

  def redraw = repaint

  def setBackground(p: Panel) =
    p.background =
      if (controller.isGiven(row, column)) givenCellColor
      else if (controller.isHighlighted(row, column)) highlightedCellColor
      else cellColor
}
