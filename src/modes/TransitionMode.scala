package sky.aether
package modes

import in.dogue.antiqua.Antiqua.Cell
import in.dogue.antiqua.Antiqua.*
import in.dogue.antiqua.graphics.{Tile, TileRenderer}

import scala.collection.mutable.ArrayBuffer

object TransitionMode {
  val tSpeed = 60
  def mkTransition(old:Mode, `new`:Mode):Mode = {
    TransitionMode(old, `new`, 0, tSpeed).toMode
  }
}

case class TransitionMode(old:Mode, `new`:Mode, t:Int, maxTime:Int) {
  def update:Mode = {
    if t >= maxTime then
      Game.Transitioning = false
      `new`
    else
      Game.Transitioning = true
      copy(old=old.update, `new`=`new`.update,t=t+1).toMode

  }
  def draw(tr:TileRenderer):TileRenderer = {
    val c = Game.Cols/2
    val r = Game.Rows/2
    val maxRadius = Math.sqrt(r*r + c*c).toInt
    val radius = (1 - t.toDouble/maxTime) * maxRadius
    val draws = old.draw(tr).getDraws
    val ab:ArrayBuffer[(Cell,Tile)] = new ArrayBuffer[(Cell,Tile)]()
    val center = (Game.Cols/2, Game.Rows/2)
    for (o <- draws) {
      if (o._1 |-| center).mag < radius then
        ab += o
    }
    tr <+< `new`.draw <++ ab.toSeq
  }
  def toMode: Mode = Mode[TransitionMode](_.update, _.draw, this)
}
