package de.htwg.se.sogo.controller

import de.htwg.se.sogo.model.{GameBoard, GamePiece, GamePieceColor, Player}
import de.htwg.se.sogo.util.Observable

class Controller(var gameBoard: GameBoard) extends Observable {
    val players = Vector(
        new Player("Player 1", GamePieceColor.RED),
        new Player("Player 2", GamePieceColor.BLUE)
    )
    var currentPlayer = 0

    def getCurrentPlayer: Player = players(currentPlayer)

    def createEmptyGameBoard(size: Int): Unit = {
        gameBoard = new GameBoard(size,size,size)
        notifyObservers
    }

    def put(x: Int, y: Int): Unit = {
        val piece = new GamePiece(players(currentPlayer).color)
        gameBoard = gameBoard.placePiece(piece, (x, y))
        currentPlayer = (currentPlayer + 1) % players.length
        notifyObservers
    }

    def get(x: Int, y: Int, z: Int): Option[GamePiece] = {
        gameBoard.retrievePiece(x,y,z)
    }
}
