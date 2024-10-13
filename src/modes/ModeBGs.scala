package sky.aether
package modes

import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.data.CP437
import in.dogue.antiqua.Antiqua.*
import in.dogue.antiqua.graphics.{Border, Rect, TileRenderer}

import scala.util.Random

object ModeBGs {

}

abstract class ModeBG {
  def update:ModeBG
  def draw(tr:TileRenderer):TileRenderer
}

case class TitleModeBG() extends ModeBG {
  val border = Border(CP437.doubleBorder)(Color.Black, Color.White)(Game.Cols, Game.Rows).toTileGroup
  val bg = Rect.createSimpleTextured(
    Game.Cols, Game.Rows, Color.Black, 2, Color.White.dim(5), 2,
    Vector(CP437.`╬`, CP437.`|`, CP437.` `, CP437.`=`, CP437.-, CP437.║),
    new Random()
  ).toTileGroup
  override def update = TitleModeBG()

  override def draw(tr: TileRenderer): TileRenderer =
    tr <++ bg <++ border
}

case class CharacterSelectBG() extends ModeBG {
  val border = Border(CP437.doubleBorder)(Color.Black, Color.White)(Game.Cols, Game.Rows).toTileGroup
  val bg = Rect.createSimpleTextured(
    Game.Cols, Game.Rows, Color.Black, 2, Color.Green.dim(5), 2,
    Vector(CP437.`α`, CP437.`δ`, CP437.` `, CP437.`.`, CP437.-, CP437.`,`),
    new Random()
  ).toTileGroup
  override def update = CharacterSelectBG()

  override def draw(tr: TileRenderer): TileRenderer =
    tr <++ bg <++ border
}
