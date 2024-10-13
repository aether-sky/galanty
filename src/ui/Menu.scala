package sky.aether
package ui

import input.Controls
import modes.{CharacterMode, Mode, ProfileMode}

import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.Antiqua.Cell
import in.dogue.antiqua.Antiqua.*
import in.dogue.antiqua.graphics.TileRenderer

object Menu {
  val spacing = 2

  def mkMainMenu: Menu = {
    val menuPos = (47, 25)
    val buttons = Vector(
      "Profile select",
      "       Options",
      "          Exit"
    ).zipWithIndex.map((s, j) =>
      val bpos = (menuPos |+| (0, j + spacing * j))
      Button(bpos, s.toTileGroup(Color.Black, Color.White), false))
    Menu(menuPos, buttons)
  }
}

case class Menu(pos:Cell, buttons:IndexedSeq[Button]) {
  def update:(Menu,Option[Mode]) = {
    val nbs = buttons.map(_.update)
    for (i <- nbs.indices) {
      val b = nbs(i)
      if Controls.Click.justPressed && b.selected then
        return i match {
          case 2 => sys.exit(0)
          case 0 => (this, Some(ProfileMode.mkProfileMode))
          case 1 => (this, None)
        }
    }
    (copy(buttons=nbs),None)
  }

  def draw(tr:TileRenderer):TileRenderer = {
    tr <++< buttons.map(_.draw)
  }
}

