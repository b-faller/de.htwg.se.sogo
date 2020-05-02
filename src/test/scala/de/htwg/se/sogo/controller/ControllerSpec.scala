package de.htwg.se.sogo.controller

import scala.language.reflectiveCalls

import de.htwg.se.sogo.model.GameBoard
import de.htwg.se.sogo.util.Observer

import org.scalatest.wordspec.{AnyWordSpec}
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyWordSpec with Matchers {

    def fixture = new {
        val gameBoard = new GameBoard(4, 4, 4)
        val controller = new Controller(gameBoard)
        val observer = new Observer {
            var updated: Boolean = false
            def isUpdated: Boolean = updated
            override def update: Unit = {this.updated = true}
        }
        controller.add(observer)
    }

    "A Controller" when {
        "observed by an observer" should {
            "notify its Observer after creation" in {
                val f = fixture
                f.observer.isUpdated should be(false)
                val newGameBoard = new GameBoard(3, 3, 3)
                f.controller.createEmptyGameBoard(3)
                f.controller.gameBoard should be(newGameBoard)
                f.observer.isUpdated should be(true)
            }
            "notify its Observer after GamePiece placement" in {
                val f = fixture
                f.observer.isUpdated should be(false)
                f.controller.put(1,1)
                f.observer.isUpdated should be(true)
            }
        }
    }
}
