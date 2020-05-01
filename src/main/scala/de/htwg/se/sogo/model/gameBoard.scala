package de.htwg.se.sogo.model

class gameBoard(var dimX: Int, var dimY: Int, var dimZ: Int) {
    private var boardArray = Array.ofDim[Option[GamePiece]](dimX,dimY,dimZ)

    def placePiece(piece: GamePiece, pos: (Int, Int, Int)) {
        boardArray(pos) = piece
    }

    var retrievePiece(pos: (Int, Int, Int)): GamePiece = boardArray(pos)
}