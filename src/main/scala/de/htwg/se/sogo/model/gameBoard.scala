package de.htwg.se.sogo.model

import scala.collection.mutable.StringBuilder

case class GameBoard(boardVect: Vector[Vector[Vector[Option[GamePiece]]]]) {
    def this(dimX: Int, dimY: Int, dimZ: Int) = this(Vector.fill(dimX,dimY,dimZ)(None))

    def placePiece(piece: Option[GamePiece], pos: (Int, Int, Int)): GameBoard = {
        return copy(boardVect.updated(pos._1,boardVect(pos._1).updated(pos._2, boardVect(pos._1)(pos._2).updated(pos._3, piece))))
    }

    def placePiece(piece: GamePiece, pos: (Int, Int)): GameBoard = {
        var i = 0
        while(boardVect(pos._1)(pos._2)(i) != None) {
            i += 1
        }
        return copy(boardVect.updated(pos._1,boardVect(pos._1).updated(pos._2, boardVect(pos._1)(pos._2).updated(i, Some(piece)))))
    }

    def retrievePiece(pos: (Int, Int, Int)): Option[GamePiece] = boardVect(pos._1)(pos._2)(pos._3)

    override def toString: String = {
        val newline = System.getProperty("line.separator")
        var sb = new StringBuilder

        val dimX = boardVect.length
        val dimY = boardVect(0).length
        val dimZ = boardVect(0)(0).length
    
        for {i <- 0 until dimZ} {
            sb ++= "Plane " + i ++= newline
            sb ++= "-" * (2 * dimX + 1) ++= newline
            for {j <- 0 until dimY} {
                for {k <- 0 until dimX} {
                    sb += '|' ++= {
                        boardVect(k)(j)(i) match{
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
