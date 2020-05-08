package de.htwg.se.sogo.controller

import de.htwg.se.sogo.model.{GameBoard, GamePiece, GamePieceColor}
import de.htwg.se.sogo.util.Observable

class Controller(var gameBoard: GameBoard) extends Observable {
    def createEmptyGameBoard(size: Int): Unit = {
        gameBoard = new GameBoard(size,size,size)
        notifyObservers
    }

    def put(x: Int, y: Int): Unit = {
        val piece = new GamePiece(GamePieceColor.RED)
        gameBoard = gameBoard.placePiece(piece, (x, y))
        notifyObservers
    }
}
