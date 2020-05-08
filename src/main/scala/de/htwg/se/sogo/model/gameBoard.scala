package de.htwg.se.sogo.model

case class GameBoard(boardVect: Vector[Vector[Vector[Option[GamePiece]]]]) {
    def this(dimX: Int, dimY: Int, dimZ: Int) = this(Vector.fill(dimX,dimY,dimZ)(None))

    def placePiece(piece: Option[GamePiece], pos: (Int, Int, Int)): GameBoard = {
        return copy(boardVect.updated(pos._1,boardVect(pos._1).updated(pos._2, boardVect(pos._1)(pos._2).updated(pos._3, piece))))
    }

    def retrievePiece(pos: (Int, Int, Int)): Option[GamePiece] = boardVect(pos._1)(pos._2)(pos._3)
}