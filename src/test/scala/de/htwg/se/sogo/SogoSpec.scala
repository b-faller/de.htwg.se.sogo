package de.htwg.se.sogo

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class SogoSpec extends AnyWordSpec with Matchers {
    "The Sogo main" should {
        "run" in {
            Sogo.main(Array[String]("q"))
        }
    }
}
