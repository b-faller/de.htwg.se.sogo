package de.htwg.se.sogo.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import scala.io.Source._
import de.htwg.se.sogo.controller.controllerComponent._
import scala.swing.Slider

class SwingGui(controller: ControllerInterface) extends Frame {

  listenTo(controller)

  title = "Sogo (not your email client)"

  def planeText: String = { "Showing plane: " + controller.getActiveBoardLayer }
  val planeLabel = new Label { planeText }
  var pieces = Array.ofDim[GamePiecePanel](
    controller.gameBoardSize,
    controller.gameBoardSize
  )

  def controlPanel: FlowPanel = new FlowPanel {
    val layerSlider = new Slider {
      min = 0
      max = 3
      majorTickSpacing = 1
      snapToTicks = true
      paintTicks = true
      paintLabels = true
      value = 0;
      reactions += {
        case event: ValueChanged => {
          controller.setActiveBoardLayer(value)
          redraw
        }
      }
      redraw
    }
    val planeUp = Button("Up") {
      if (controller.getActiveBoardLayer < controller.gameBoardSize - 1) {
        controller.setActiveBoardLayer(controller.getActiveBoardLayer + 1)
        layerSlider.value = controller.getActiveBoardLayer
        redraw
      }
    }
    val planeDown = Button("Down") {
      if (controller.getActiveBoardLayer > 0) {
        controller.setActiveBoardLayer(controller.getActiveBoardLayer - 1)
        layerSlider.value = controller.getActiveBoardLayer
        redraw
      }
    }
    contents += planeLabel
    contents += planeUp
    contents += planeDown
    contents += layerSlider
  }

  def gameBoardPanel =
    new GridPanel(controller.gameBoardSize, controller.gameBoardSize) {
      for {
        y <- 0 until controller.gameBoardSize
        x <- 0 until controller.gameBoardSize
      } {
        val gpPanel = new GamePiecePanel(x, y, controller.getActiveBoardLayer, controller)
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
          controller.createNewGameBoard(3)
          gameBoardPanel
        })
        contents += new MenuItem(Action("Size 4^3") {
          controller.createNewGameBoard(4)
          gameBoardPanel
        })
      }
      contents += new MenuItem(Action("Open") { /* load game from file */ })
      contents += new MenuItem(Action("Save") { /* save game to file */ })
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
    }
  }

  defaultWindowSize  
  visible = true
  redraw

  reactions += {
    case event: BoardChanged => {
      resize(controller.gameBoardSize)
      redraw
    }
  }

  def defaultWindowSize = {
    this.size = new Dimension(500,600)
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
    defaultWindowSize
  }

  def redraw = {
    planeLabel.text = planeText
    statusline.text = controller.statusText
    repaint
  }
}
