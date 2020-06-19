package de.htwg.se.sogo.model

import scala.collection.mutable.StringBuilder
import scala.util.Try

object GameBoardFactory {
  def apply(s: String): GameBoard = s match {
    case "small"    => new GameBoard(3)
    case "standard" => new GameBoard(4)
  }
}

case class GameBoard(boardVect: Vector[Vector[Vector[Option[GamePiece]]]]) {
  def this(dimX: Int, dimY: Int, dimZ: Int) =
    this(Vector.fill(dimX, dimY, dimZ)(None))

  def this(d: Int) = this(d, d, d)

  def dim(): Int = boardVect.length

  def isEmpty: Boolean = {
    boardVect.flatten.flatten.forall((p: Option[GamePiece]) => p.isEmpty)
  }

  def set(piece: Option[GamePiece], pos: (Int, Int, Int)): GameBoard = {
    return copy(
      boardVect.updated(
        pos._1,
        boardVect(pos._1)
          .updated(pos._2, boardVect(pos._1)(pos._2).updated(pos._3, piece))
      )
    )
  }

  def get(pos: (Int, Int, Int)): Option[GamePiece] =
    boardVect(pos._1)(pos._2)(pos._3)

  def placePiece(piece: GamePiece, pos: (Int, Int)): Try[GameBoard] = {
    Try({
      var i = 0
      while (boardVect(pos._1)(pos._2)(i) != None) {
        i += 1
      }
      set(Some(piece), (pos._1, pos._2, i))
    })
  }

  def popPiece(pos: (Int, Int)): (GameBoard, Option[GamePiece]) = {
    for (z <- dim - 1 to 0 by -1) {
      val piece = get((pos._1, pos._2, z))
      if (piece.isDefined) {
        return (set(None, (pos._1, pos._2, z)), piece)
      }
    }
    (this, None)
  }

  def hasWon(color: GamePieceColor.Value): Boolean = {
    if (hasWonX(color)) return true
    if (hasWonY(color)) return true
    if (hasWonXAscending(color)) return true
    if (hasWonXDescending(color)) return true
    if (hasWonYAscending(color)) return true
    if (hasWonYDescending(color)) return true
    if (hasWonDiagonally(color)) return true
    if (hasWonDiagonallyAscending(color)) return true
    if (hasWonDiagonallyDescending(color)) return true
    false
  }

  def hasWonX(color: GamePieceColor.Value): Boolean = {
    for {
      y <- 0 until dim
      z <- 0 until dim
    } {
      val xRow = for (x <- 0 until dim) yield boardVect(x)(y)(z)
      if (xRow.forall(gp => isGamePieceColorEqual(gp, color))) return true
    }

    false
  }

  def hasWonY(color: GamePieceColor.Value): Boolean = {
    for {
      x <- 0 until dim
      z <- 0 until dim
    } {
      val yRow = for (y <- 0 until dim) yield boardVect(x)(y)(z)
      if (yRow.forall(gp => isGamePieceColorEqual(gp, color))) return true
    }

    false
  }

  def hasWonXAscending(color: GamePieceColor.Value): Boolean = {
    for {
      y <- 0 until dim
    } {
      val diagonalRow = for (s <- 0 until dim) yield boardVect(s)(y)(s)
      if (diagonalRow.forall(gp => isGamePieceColorEqual(gp, color)))
        return true
    }

    false
  }

  def hasWonXDescending(color: GamePieceColor.Value): Boolean = {
    for {
      y <- 0 until dim
    } {
      val diagonalRow =
        for (s <- 0 until dim) yield boardVect(s)(y)(dim - s - 1)
      if (diagonalRow.forall(gp => isGamePieceColorEqual(gp, color)))
        return true
    }

    false
  }

  def hasWonYAscending(color: GamePieceColor.Value): Boolean = {
    for {
      x <- 0 until dim
    } {
      val diagonalRow = for (s <- 0 until dim) yield boardVect(x)(s)(s)
      if (diagonalRow.forall(gp => isGamePieceColorEqual(gp, color)))
        return true
    }

    false
  }

  def hasWonYDescending(color: GamePieceColor.Value): Boolean = {
    for {
      x <- 0 until dim
    } {
      val diagonalRow =
        for (s <- 0 until dim) yield boardVect(x)(s)(dim - s - 1)
      if (diagonalRow.forall(gp => isGamePieceColorEqual(gp, color)))
        return true
    }

    false
  }

  def hasWonDiagonally(color: GamePieceColor.Value): Boolean = {
    for {
      z <- 0 until dim
    } {
      val diagonalRow =
        for (s <- 0 until dim) yield boardVect(s)(s)(z)
      if (diagonalRow.forall(gp => isGamePieceColorEqual(gp, color)))
        return true
    }

    for {
      z <- 0 until dim
    } {
      val diagonalRow =
        for (s <- 0 until dim) yield boardVect(dim - s - 1)(s)(z)
      if (diagonalRow.forall(gp => isGamePieceColorEqual(gp, color)))
        return true
    }

    false
  }

  def hasWonDiagonallyAscending(color: GamePieceColor.Value): Boolean = {
    var row = for (s <- 0 until dim) yield boardVect(s)(s)(s)
    if (row.forall(gp => isGamePieceColorEqual(gp, color)))
      return true

    row = for (s <- 0 until dim) yield boardVect(dim - s - 1)(s)(s)
    if (row.forall(gp => isGamePieceColorEqual(gp, color)))
      return true

    false
  }

  def hasWonDiagonallyDescending(color: GamePieceColor.Value): Boolean = {
    var row = for (s <- 0 until dim) yield boardVect(s)(dim - s - 1)(s)
    if (row.forall(gp => isGamePieceColorEqual(gp, color)))
      return true

    row = for (s <- 0 until dim) yield boardVect(dim - s - 1)(dim - s - 1)(s)
    if (row.forall(gp => isGamePieceColorEqual(gp, color)))
      return true

    false
  }

  def isGamePieceColorEqual(
      gamePiece: Option[GamePiece],
      color: GamePieceColor.Value
  ): Boolean = {
    gamePiece match {
      case Some(gamePiece) => gamePiece.color == color
      case None            => false
    }
  }

  override def toString: String = {
    val newline = System.getProperty("line.separator")
    var sb = new StringBuilder

    for { i <- 0 until dim } {
      sb ++= "Plane " + i ++= newline
      sb ++= "-" * (2 * dim + 1) ++= newline
      for { j <- 0 until dim } {
        for { k <- 0 until dim } {
          sb += '|' ++= {
            boardVect(k)(j)(i) match {
              case Some(c) => c.toString
              case None    => " "
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
