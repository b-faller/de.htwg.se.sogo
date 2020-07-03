package de.htwg.se.sogo.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import scala.io.Source._
import de.htwg.se.sogo.controller.controllerComponent._

class SwingGui(controller: ControllerInterface) extends Frame {

  listenTo(controller)

  title = "Sogo (not your email client)"
  var selectedPlane = 0

  def planeText: String = { "Showing plane: " + selectedPlane }
  val planeLabel = new Label { planeText }
  var pieces = Array.ofDim[GamePiecePanel](
    controller.gameBoardSize,
    controller.gameBoardSize,
    controller.gameBoardSize
  )

  def controlPanel = new FlowPanel {
    val planeUp = Button("Up") {
      if (selectedPlane < controller.gameBoardSize - 1) {
        selectedPlane += 1
        planeLabel.text = planeText
        redraw
      }
    }
    val planeDown = Button("Down") {
      if (selectedPlane > 0) {
        selectedPlane -= 1
        planeLabel.text = planeText
        redraw
      }
    }
    contents += planeLabel
    contents += planeUp
    contents += planeDown
  }

  def gameBoardPanel =
    new GridPanel(controller.gameBoardSize, controller.gameBoardSize) {
      border = LineBorder(java.awt.Color.BLACK, 1)
      background = java.awt.Color.BLACK
      for {
        x <- 0 until controller.gameBoardSize
        y <- 0 until controller.gameBoardSize
        z <- 0 until controller.gameBoardSize
      } {
        val gpPanel = new GamePiecePanel(x, y, z, controller)
        pieces(x)(y)(z) = gpPanel
        if (z == selectedPlane)
          contents += gpPanel
        listenTo(gpPanel)
      }
    }

  val statusline = new TextField(controller.statusText, 20)

  def panel =new BorderPanel {
    add(controlPanel, BorderPanel.Position.North)
    add(gameBoardPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  contents = panel

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new Menu("New") {
        contents += new MenuItem(Action("Size 3^3") {
          gameBoardPanel
          controller.createNewGameBoard(3)
        })
        contents += new MenuItem(Action("Size 4^3") {
          gameBoardPanel
          controller.createNewGameBoard(4)
        })
      }
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
    }
  }

  visible = true
  redraw

  reactions += {
    case event: BoardContentChanged => redraw
    case event: BoardChanged        => resize(3)
  }

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
    contents = panel
    repaint
  }
}
