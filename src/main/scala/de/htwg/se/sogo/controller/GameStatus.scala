package de.htwg.se.sogo.controller

object GameStatus extends Enumeration{
  type GameStatus = Value
  val RED_TURN, BLUE_TURN, RED_WON, BLUE_WON, DRAW = Value

  val map = Map[GameStatus, String](
    RED_TURN -> "Player Red's turn",
    BLUE_TURN -> "Player Blue's turn",
    RED_WON -> "Player Red has won",
    BLUE_WON -> "Player Blue has won",
    DRAW -> "Game has ended in a draw"
    );

  val playermap = Map[GameStatus, Int](
    RED_TURN -> 0,
    BLUE_TURN -> 1
  );

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

  def player(gameStatus: GameStatus) = {
    playermap(gameStatus)
  }
}