package de.htwg.se.sogo.model

case class GameBoard(var dimX: Int, var dimY: Int, var dimZ: Int) {
    //private var boardArray = Array.ofDim[Option[GamePiece]](dimX,dimY,dimZ)
    private var boardArray = Array.fill(dimX,dimY,dimZ)(None:Option[GamePiece])

    def placePiece(piece: Option[GamePiece], pos: (Int, Int, Int)): Unit = {
        boardArray(pos._1)(pos._2)(pos._3) = piece
    }

    def retrievePiece(pos: (Int, Int, Int)): Option[GamePiece] = boardArray(pos._1)(pos._2)(pos._3)
}