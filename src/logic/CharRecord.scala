package sky.aether
package logic

import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.graphics.TileRenderer
import in.dogue.antiqua.Antiqua.*
import in.dogue.antiqua.data.CP437

object CharRecord {
  def load(profileName:String):Seq[CharRecord] = Seq(CharRecord("default", 1, 1, Nil))
}


case class CharRecord(name:String, hunger:Int, power:Int, inventory:Seq[String]) {
  def draw(pos:Cell)(tr:TileRenderer):TileRenderer = {
    tr//CP437.☻.mkTile(Color.Black, Color.White)
  }
}
