package sky.aether
package modes

import com.deweyvm.gleany.data.Recti
import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.graphics.{Border, TileRenderer}
import in.dogue.antiqua.Antiqua.*
import input.Controls

import sky.aether.logic.CharRecord
import sky.aether.ui.Button

object CharacterMode {
  def mkCharacterMode(chars:Seq[CharRecord]):Mode = {
    CharacterMode(CharSelectMenu.mkMenu(chars), CharacterSelectBG()).toMode
  }
}

class CharSelectButton(pos:Cell, val char:CharRecord) {
  val width = 10
  val height = 10

  def rect: Recti = {
    Button.makeHitbox(pos, width, height)
  }

  def update: CharSelectButton = {
    this
  }

  def selected = {
    rect.contains(Controls.mousePos)
  }

  val border = Border.double(Color.Black, Color.White)(width,height)

  def draw(tr: TileRenderer): TileRenderer = {
    val trr = if selected then
      tr <++ "SELECTED".toTileGroup(Color.Black, Color.White)
    else
      tr
    trr <++ (border.toTileGroup |++| pos)
        <++ (char.name.toTileGroup(Color.Black, Color.White) |++| (pos |+| (1, 1)))
      //<++ (profile.created.toString.toTileGroup(Color.Black, Color.White) |++| (pos |+| (1, 2)))
  }


}

object CharSelectMenu {
  def mkMenu(characters:Seq[CharRecord]):CharSelectMenu = {
    val pos = (10,10)
    val buttons = characters.zipWithIndex.map((c, j) =>
      CharSelectButton((0,j*10) |+| pos, c)
    )
    CharSelectMenu(buttons.toIndexedSeq)
  }
}

case class CharSelectMenu(buttons:IndexedSeq[CharSelectButton]) {
  def update: (CharSelectMenu, Option[Mode]) = {
    val nbs = buttons.map(_.update)
    if Controls.Click.justPressed then
      println("here1")

    for (i <- nbs.indices) {
      val b = nbs(i)
      if Controls.Click.justPressed && b.selected then
        println("here")
        return i match {
          case 2 => sys.exit(0)
          case 0 => (this, Some(GameMode.mkGameMode(buttons(i).char)))
          case 1 => (this, None)
        }
    }
    (copy(buttons = nbs), None)
  }

  def draw(tr: TileRenderer): TileRenderer = {
    tr <++< buttons.map(_.draw)
  }
}

case class CharacterMode(menu:CharSelectMenu, bg:ModeBG) {
  def update:Mode =
    menu.update match {
      case (_, Some(m)) => TransitionMode.mkTransition(this.toMode, m)
      case (nm, None) => copy(bg=bg.update,menu=nm).toMode
    }
  def draw(tr:TileRenderer):TileRenderer =
    val label = "Select character:".toTileGroup(Color.Black,Color.White)
    tr <+< bg.draw <+< menu.draw <++ (label |++| (3,3))
  def toMode: Mode = Mode[CharacterMode](_.update, _.draw, this)
}
