package de.htwg.se.sogo.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class UndoManagerSpec extends AnyWordSpec with Matchers {

  "An UndoManager" should {
    "have a do, undo and redo" in {
      val undoManager = new UndoManager
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.undoStep
      command.state should be(0)
      undoManager.redoStep
      command.state should be(1)
    }

    "handle undo on an empty stack" in {
      val undoManager = new UndoManager
      val command = new incrCommand
      command.state should be(0)
      undoManager.undoStep
      command.state should be(0)
    }

    "handle redo on an empty stack" in {
      val undoManager = new UndoManager
      val command = new incrCommand
      command.state should be(0)
      undoManager.redoStep
      command.state should be(0)
    }

    "handle multiple undo steps correctly" in {
      val undoManager = new UndoManager
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.doStep(command)
      command.state should be(2)
      undoManager.undoStep
      command.state should be(1)
      undoManager.undoStep
      command.state should be(0)
      undoManager.redoStep
      command.state should be(1)
    }
  }
}
