package de.htwg.se.sogo.aview.gui

import scala.swing._
import scala.swing.event._
import scala.swing.Swing.LineBorder

import de.htwg.se.sogo.controller.controllerComponent.{
  BoardChanged,
  ControllerInterface
}
import de.htwg.se.sogo.model.{GamePieceColor, GamePiece}

class GamePiecePanel(x: Int, y: Int, z: Int, controller: ControllerInterface)
    extends FlowPanel {

  val noPieceColor = new Color(50, 50, 50)
  val redColor = new Color(255, 0, 0)
  val blueColor = new Color(0, 0, 255)

  def myPiece = controller.get(x, y, z)

  def pieceColor: Color = {
    if (myPiece.isEmpty) {
      noPieceColor
    } else if (myPiece.get.color == GamePieceColor.RED) {
      redColor
    } else {
      blueColor
    }
  }

  border = LineBorder(java.awt.Color.BLACK, 2)
  background = pieceColor
  listenTo(mouse.clicks)
  listenTo(controller)
  reactions += {
    case MouseClicked(src, pt, mod, clicks, pops) => {
      controller.put(x, y)
      repaint
    }
  }

  def redraw = {
    background = pieceColor
    repaint
  }
}
