package de.htwg.se.sogo.aview

import java.security.Permission
import java.nio.file.{Paths, Files}
import scala.language.reflectiveCalls

import de.htwg.se.sogo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.sogo.model.fileIOComponent._
import de.htwg.se.sogo.model.gameBoardComponent.gameBoardBaseImpl.GameBoard
import de.htwg.se.sogo.model.{GamePiece, GamePieceColor}

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

sealed case class ExitException(status: Int)
    extends SecurityException("System.exit() is not allowed") {}

sealed class NoExitSecurityManager extends SecurityManager {
  override def checkPermission(perm: Permission): Unit = {}

  override def checkPermission(perm: Permission, context: Object): Unit = {}

  override def checkExit(status: Int): Unit = {
    super.checkExit(status)
    throw ExitException(status)
  }
}

class TuiSpec extends AnyWordSpec with Matchers {

  def fixture = new {
    val fileIo = new fileIOJsonImpl.FileIO()
    val controller = new Controller(new GameBoard(4), fileIo)
    val tui = new Tui(controller)
  }

  "A Sogo Tui" should {
    "create an empty Sogo on input 'n'" in {
      val f = fixture
      f.tui.processInputLine("n")
      val newGameBoard = new GameBoard(4)
      f.controller.gameBoard should be(newGameBoard)
    }
    "undo a step on input 'u'" in {
      val f = fixture
      f.tui.processInputLine("00")
      f.tui.processInputLine("u")
      f.controller.gameBoard.get((0, 0, 0)) should be(None)
    }
    "redo a step on input 'r'" in {
      val f = fixture
      f.tui.processInputLine("00")
      f.tui.processInputLine("u")
      f.tui.processInputLine("r")
      f.controller.gameBoard.get((0, 0, 0)) should be(
        Some(GamePiece(GamePieceColor.RED))
      )
    }
    "put a GamePiece on input '23'" in {
      val f = fixture
      f.tui.processInputLine("23")
      f.controller.gameBoard.get(2, 3, 0) should be(
        Some(GamePiece(GamePieceColor.RED))
      )
    }
    "save/load the game" in {
      val f = fixture
      f.tui.processInputLine("23")
      f.tui.processInputLine("s")
      Files.exists(Paths.get("sogoGame.json")) || Files.exists(
        Paths.get("sogoGame.xml")
      ) should be(true)
      f.tui.processInputLine("n")
      f.tui.processInputLine("l")
      f.controller.gameBoard.get(2, 3, 0) should be(
        Some(GamePiece(GamePieceColor.RED))
      )
    }
    "ignore an malformed put instruction" in {
      val f = fixture
      f.tui.processInputLine("222")
    }
    "ignore an out of bounds put instruction" in {
      val f = fixture
      f.tui.processInputLine("99")
    }
    "ignore an Null Pointer Exception" in {
      val f = fixture
      f.tui.processInputLine(null)
    }
    "ignore an invalid put when no vertical space is free" in {
      val f = fixture
      f.tui.processInputLine("00")
      f.tui.processInputLine("00")
      f.tui.processInputLine("00")
      f.tui.processInputLine("00")
      f.tui.processInputLine("00")
    }
    "ignore an arbitrary input" in {
      val f = fixture
      f.tui.processInputLine("something went wrong")
    }
    "should exit on q" in {
      System.setSecurityManager(new NoExitSecurityManager())
      val f = fixture
      val thrown = intercept[Exception] {
        f.tui.processInputLine("q")
      }
      thrown.getMessage should be("System.exit() is not allowed")
      System.setSecurityManager(null)
    }
  }
}
