package sky.aether
package procgen

import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.data.{Array2d, CP437, Code}
import in.dogue.antiqua.graphics.{Tile, TileRenderer}
import in.dogue.antiqua.Antiqua
import Antiqua.*

import scala.util.Random



object SegmentType {
  case object Outer extends SegmentType
  case object Inner extends SegmentType
}
trait SegmentType
case class Segment(`type`:SegmentType, code:Code) {
  //def draw(tr:TileRenderer)
}

case class Floor(segments:IndexedSeq[Segment])

class Babel(floors:List[Floor]) {

}

object Tower {
  def generate:Tower = {
    val random = new Random()
    val width =  random.nextInt(4) + 4
    val height = random.nextInt(8) + 8
    val layout = Array2d.tabulate(width,height) { case (i, j) =>
      Tile(CP437.`=`, Color.Black, Color.White)
    }
    val tString = """█-=-=-=-=-=-=█
                   | █=-=-=-=-=-=-█
                   | █▀▀▀▀▀▀▀▀▀▀▀▀█
                   | █=-=-=-=-=-=-█
                   | █-=-=-=-=-=-=█
                   | █▀▀▀▀▀▀▀▀▀▀▀▀█
                   | █-=-=-=-=-=-=█
                   |  =-=-=-=-=-=-█
                   | ▀▀▀▀▀▀▀▀▀▀▀▀▀▀""".stripMargin
    val tLines: Array[String] = tString.split("\n")
    val h = tLines.length
    val w = tLines(0).length
    val nTower = Array2d.tabulate(w, h) { (i, j) =>
      Tile(CP437.unicodeToCode(tLines(j)(i)), Color.Black, Color.White)
    }
    Tower(w, h, nTower)
  }

}

class Tower(width:Int, height:Int, tiles:Array2d[Tile]) {
  def draw(pos:Cell)(tr:TileRenderer):TileRenderer = {
    tr <++ tiles.flatten.map { case (p, t) => (p |+| pos, t) }
  }
}
