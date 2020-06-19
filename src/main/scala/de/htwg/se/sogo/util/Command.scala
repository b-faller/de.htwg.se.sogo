package de.htwg.se.sogo.util

trait Command {
  def doStep: Unit
  def undoStep: Unit
  def redoStep: Unit
}
