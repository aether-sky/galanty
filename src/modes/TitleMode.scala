package sky.aether
package modes

import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.data.CP437
import in.dogue.antiqua.graphics.{Tile, TileRenderer}
import in.dogue.antiqua.Antiqua.*
import ui.Menu

object TitleMode {
  def mkTitleMode:TitleMode = {
    TitleMode(Menu.mkMainMenu, TitleModeBG())
  }
}

case class TitleMode(menu:Menu, bg:ModeBG) {
  val title = Tile.groupFromFile("title", "tiles", CP437.intToCode, _.mkTile(Color.Black.dim(4), Color.White.dim(1))).filter { case (_, t) =>
    t.code != CP437.` `.toCode
  }

  def update:Mode = {
    menu.update match {
      case (_, Some(m)) => TransitionMode.mkTransition(this.toMode,m)
      case (nm, None) => copy(menu=nm,bg=bg.update).toMode
    }
  }
  def draw(tr:TileRenderer):TileRenderer =
    tr <+< bg.draw <++ (title |++| (40,10)) <+< menu.draw
  def toMode: Mode = Mode[TitleMode](_.update, _.draw, this)

}
