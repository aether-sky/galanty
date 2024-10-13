package sky.aether
package modes

import in.dogue.antiqua.graphics.{Border, Doodad, TileRenderer}
import in.dogue.antiqua.Antiqua.*
import data.NonEmptyList
import logic.Profile

import com.deweyvm.gleany.data.Recti
import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.data.CP437
import input.Controls
import ui.{Button, Menu}

case class ProfileButton(pos:Cell, profile:Profile) {
  val width = 10
  val height = 10
  def rect: Recti = {
    Button.makeHitbox(pos, width, height)
  }

  def update: ProfileButton = {
    this
  }

  def selected = {
    rect.contains(Controls.mousePos)
  }

  val border = Border.double(Color.Black, Color.White)(10, 10)

  def draw(tr: TileRenderer): TileRenderer = {
    val trr = if selected then
      tr <++ "SELECTED".toTileGroup(Color.Black,Color.White)
    else
      tr
    trr <++ (border.toTileGroup |++| pos)
        <++ (profile.name.toTileGroup(Color.Black, Color.White) |++| (pos |+| (1, 1)))
        <++ (profile.created.toString.toTileGroup(Color.Black, Color.White) |++| (pos |+| (1, 2)))
  }


}

case class ProfileMenu(buttons:IndexedSeq[ProfileButton]) {
  def update:(ProfileMenu,Option[Mode]) = {
    val nbs = buttons.map(_.update)
    for (i <- nbs.indices) {
      val b = nbs(i)
      if Controls.Click.justPressed && b.selected then
        return i match {
          case 2 => sys.exit(0)
          case 0 => 
            val selected = buttons(i)
            (this, Some(CharacterMode.mkCharacterMode(selected.profile.characters)))
          case 1 => (this, None)
        }
    }
    (copy(buttons=nbs),None)
  }

  def draw(tr:TileRenderer):TileRenderer = {
    tr <++< buttons.map(_.draw)
  }
}

object ProfileMode {
  def mkProfileMode:Mode =
    val pos = (10, 10)
    val profile = Profile.load
    val menu = ProfileMenu(Vector(ProfileButton(pos, profile)))
    ProfileMode(NonEmptyList(profile), menu, TitleModeBG()).toMode
}

case class ProfileMode(profiles:NonEmptyList[Profile], menu:ProfileMenu, bg:ModeBG) {

  def update:Mode =
    menu.update match {
      case (_, Some(m)) => TransitionMode.mkTransition(this.toMode, m)
      case (nm, None) => copy(bg=bg.update, menu=nm).toMode
      
    }
  def draw(tr:TileRenderer):TileRenderer =
    val label = "Select profile:".toTileGroup(Color.Black,Color.White)
    tr <+< bg.draw
       <++ (label |++| (3,3))
       <+< menu.draw
  def toMode:Mode = Mode[ProfileMode](_.update, _.draw, this)
}
