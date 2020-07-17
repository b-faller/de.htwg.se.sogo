package de.htwg.se.sogo.model

object GameStatus extends Enumeration{
  type GameStatus = Value
  val RED_TURN = Value("RED_TURN")
  val BLUE_TURN = Value("BLUE_TURN")
  val RED_WON = Value("RED_WON")
  val BLUE_WON = Value("BLUE_WON")
  val DRAW = Value("Draw")

  val map = Map[GameStatus, String](
    RED_TURN -> "Player Red's turn",
    BLUE_TURN -> "Player Blue's turn",
    RED_WON -> "Player Red has won",
    BLUE_WON -> "Player Blue has won",
    DRAW -> "Game has ended in a draw"
    );

  val playermap = Map[GameStatus, Int](
    RED_TURN -> 0,
    BLUE_TURN -> 1,
    RED_WON -> 0,
    BLUE_WON -> 1
  );

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

  def player(gameStatus: GameStatus) = {
    playermap(gameStatus)
  }
}