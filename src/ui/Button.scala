package sky.aether
package ui

import input.Controls

import com.deweyvm.gleany.data.Recti
import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.Antiqua.{Cell, TileGroup}
import in.dogue.antiqua.Antiqua.*
import in.dogue.antiqua.data.CP437
import in.dogue.antiqua.graphics.TileRenderer

object Button {
  def makeHitbox(pos: Cell, width: Int, height: Int): Recti = {
    val x = pos.x * Game.TileSize
    val y = pos.y * Game.TileSize
    Recti(x, y, width * Game.TileSize, height * Game.TileSize).expand(Game.TileSize, Game.TileSize)
  }
}

case class Button(pos:Cell, label:TileGroup, disabled:Boolean) {
  val width = label.length
  def rect:Recti = {
    Button.makeHitbox(pos, label.length, 1)
  }
  def update:Button = {
    this
  }

  def selected = {
    rect.contains(Controls.mousePos)
  }

  def draw(tr:TileRenderer):TileRenderer = {
    val ntr = if selected then
      val cursors = Vector(
        ((pos |+| (-1, 0)), CP437.>.mkTile(Color.Black, Color.White)),
        ((pos |+| (width + 1, 0)), CP437.<.mkTile(Color.Black, Color.White))
      )
      tr <++ cursors
    else
      tr
    ntr <++ (label |++| pos)
  }
}

