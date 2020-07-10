package de.htwg.se.sogo.aview.gui

import scala.swing._
import scala.swing.event._
import scala.swing.Swing.LineBorder

import de.htwg.se.sogo.controller.controllerComponent.{
  BoardChanged,
  BoardLayerChanged,
  BoardContentChanged,
  ControllerInterface
}
import de.htwg.se.sogo.model.{GamePieceColor, GamePiece}
import de.htwg.se.sogo.controller.controllerComponent.BoardLayerChanged

class GamePiecePanel(x: Int, y: Int, var z: Int, controller: ControllerInterface)
    extends FlowPanel {

  val noPieceColor = new Color(180, 180, 180)
  val redColor = new Color(240, 50, 50)
  val blueColor = new Color(50, 50, 240)

  def pieceColor: Color = {
    val col = controller.getGamePieceColor(x,y,z)
    if (col.isEmpty) {
      noPieceColor
    } else if (col.get == GamePieceColor.RED) {
      redColor
    } else {
      blueColor
    }
  }

  border = LineBorder(java.awt.Color.GRAY, 1)
  background = pieceColor
  listenTo(mouse.clicks)
  listenTo(controller)
  reactions += {
    case MouseClicked(src, pt, mod, clicks, pops) => {
      controller.put(x, y)
      repaint
    }
    case event: BoardContentChanged => {
      if(event.x == x && event.y == y) {
        redraw
      }
    }
    case event: BoardLayerChanged => {
        z = event.z
        redraw
    }
  }

  def redraw = {
    background = pieceColor
    repaint
  }
}
