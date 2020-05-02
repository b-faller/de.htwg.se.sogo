package de.htwg.se.sogo.util

import scala.language.reflectiveCalls

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ObservableSpec extends AnyWordSpec with Matchers {

    def fixture = new {
        val observable = new Observable
        val observer = new Observer {
            var updated: Boolean = false
            def isUpdated: Boolean = updated
            override def update: Unit = {updated = true}
        }
    }

    "An Observable" should {
        "add an Observer" in {
            val f = fixture
            f.observable.add(f.observer)
            f.observable.subscribers should contain(f.observer)
        }
        "notify an Observer" in {
            val f = fixture
            f.observable.add(f.observer)
            f.observer.isUpdated should be(false)
            f.observable.notifyObservers
            f.observer.isUpdated should be(true)
        }
        "remove an Observer" in {
            val f = fixture
            f.observable.add(f.observer)
            f.observable.remove(f.observer)
            f.observable.subscribers should not contain(f.observer)
        }
    }
}
