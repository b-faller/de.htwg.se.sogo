package de.htwg.se.sogo

import org.scalatest.{Matchers, WordSpec}

class SogoSpec extends WordSpec with Matchers {
    "The Sogo main" should {
        "run" in {
            Sogo.main(Array[String]())
        }
    }
}
