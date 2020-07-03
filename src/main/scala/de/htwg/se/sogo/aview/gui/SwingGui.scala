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
    controller.gameBoardSize
  )

  def controlPanel: FlowPanel = new FlowPanel {
    val planeUp = Button("Up") {
      if (selectedPlane < controller.gameBoardSize - 1) {
        selectedPlane += 1
        resize(controller.gameBoardSize)
        redraw
      }
    }
    val planeDown = Button("Down") {
      if (selectedPlane > 0) {
        selectedPlane -= 1
        resize(controller.gameBoardSize)
        redraw
      }
    }
    contents += planeLabel
    contents += planeUp
    contents += planeDown
  }

  def gameBoardPanel =
    new GridPanel(controller.gameBoardSize, controller.gameBoardSize) {
      for {
        y <- 0 until controller.gameBoardSize
        x <- 0 until controller.gameBoardSize
      } {
        val gpPanel = new GamePiecePanel(x, y, selectedPlane, controller)
        pieces(x)(y) = gpPanel
        contents += gpPanel
        listenTo(gpPanel)
      }
    }

  val statusline = new Label(controller.statusText)

  contents = new BorderPanel {
    add(controlPanel, BorderPanel.Position.North)
    add(gameBoardPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

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
    case event: BoardChanged => {
      resize(controller.gameBoardSize)
      redraw
    }
  }

  def resize(gridSize: Int) = {
    pieces = Array.ofDim[GamePiecePanel](
      controller.gameBoardSize,
      controller.gameBoardSize
    )
    contents = new BorderPanel {
      add(controlPanel, BorderPanel.Position.North)
      add(gameBoardPanel, BorderPanel.Position.Center)
      add(statusline, BorderPanel.Position.South)
    }
  }

  def redraw = {
    planeLabel.text = planeText
    statusline.text = controller.statusText
    repaint
  }
}
