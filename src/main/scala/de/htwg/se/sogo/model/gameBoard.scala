package de.htwg.se.sogo.model

import scala.collection.mutable.StringBuilder

case class GameBoard(var dimX: Int, var dimY: Int, var dimZ: Int) {
    //private var boardArray = Array.ofDim[Option[GamePiece]](dimX,dimY,dimZ)
    private var boardArray = Array.fill(dimX,dimY,dimZ)(None:Option[GamePiece])

    def placePiece(piece: Option[GamePiece], pos: (Int, Int, Int)): Unit = {
        boardArray(pos._1)(pos._2)(pos._3) = piece
    }

    def retrievePiece(pos: (Int, Int, Int)): Option[GamePiece] = boardArray(pos._1)(pos._2)(pos._3)

    override def toString: String = {
        val newline = System.getProperty("line.separator")
        var sb = new StringBuilder

        for {i <- 0 until dimZ} {
            sb ++= "Plane " + i ++= newline
            sb ++= "-" * (2 * dimX + 1) ++= newline
            for {j <- 0 until dimY} {
                for {k <- 0 until dimX} {
                    sb += '|' ++= {
                        boardArray(k)(j)(i) match{
                            case Some(c) => c.toString
                            case None => " "
                        }
                    }
                }
                sb += '|' ++= newline
            }
            sb ++= newline
        }
        sb.toString
    }
}
