package sky.aether
package console

import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.Antiqua.Cell
import in.dogue.antiqua.data.CP437
import in.dogue.antiqua.graphics.{Border, TileRenderer}
import in.dogue.antiqua.Antiqua.*

object Console {
  val height = 30
  val width = 60
  val visibleLines = height-5
  val border: (Int,Int) => Border = Border(CP437.doubleBorder)(Color.Black, Color.White)
  def drawBox(pos:Cell)(tr:TileRenderer):TileRenderer =
    val b = border(width,height)
    tr <+< b.draw(pos)

  def mkConsole:Console =
    Console(TextInput(), ConsoleOutput(IndexedSeq(), Console.width, Console.visibleLines, 0))
}

case class Console(input:TextInput, console:ConsoleOutput) {
  def update:Console =
    val (ti, str) = input.update
    copy(input = ti, console=str.fold(console)(console.accept).update)

  def draw(pos:Cell)(tr:TileRenderer):TileRenderer = {
    val prompt = CP437.>.mkTile(Color.Black, Color.Green)
    val divider = (0 to Console.width-3).map(i => ((i, 0),CP437.-.mkTile(Color.Black, Color.White)))
    val rendered = tr <+ (pos |+| (1, Console.height - 3), prompt)
                      <+< Console.drawBox(pos)
                      <++ (divider |++| (pos |+| (1, Console.height-4)))
                      <+< input.draw(pos |+| (3, Console.height - 3))
                      <+< console.draw(pos |+| (1,1))
    val cursor = CP437.|.mkTile(Color.Black, Color.White)
    if Game.t / 90 % 2 == 0 then
      rendered <+ (pos |+| (3 + input.length, Console.height - 3), cursor)
    else
      rendered
  }
}