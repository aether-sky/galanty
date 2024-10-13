package sky.aether
package console

import com.deweyvm.gleany.GleanyMath
import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.Antiqua.Cell
import in.dogue.antiqua.Antiqua.*
import in.dogue.antiqua.data.CP437
import in.dogue.antiqua.graphics.TileRenderer
import sky.aether.input.Controls

import scala.collection.mutable.ArrayBuffer

type ConsoleLine = (String,Color)
case class ConsoleOutput(output:IndexedSeq[ConsoleLine], width:Int, visLines:Int, cursor:Int) {
  def accept(line:String):ConsoleOutput =
    val parsed = Command.parse(line)
    val processed = Command.process(parsed)
    val colored = processed.map(p => (p, Color.Brown))
    val outputLines = ("> " + line, Color.White) +: colored
    //outputLines.foldLeft(output) { (b, l) => b.append(l) }
    appends(outputLines)
    //copy(output = output.appendedAll()

  def appends(ls:Seq[ConsoleLine]):ConsoleOutput = {
    ls.foldLeft(this){ case (b,l) => b.append(l) }
  }

  def append(l:ConsoleLine):ConsoleOutput = {
    val lines = wrap(l._1, width)
    lines.foldLeft(this)( (a, b) =>
      copy(output = a.output.appended( (b, l._2)))
    )
  }
  def wrap(l:String, width:Int):IndexedSeq[String] = {
    val lines = ArrayBuffer[String]()
    val line = StringBuilder()
    val words = l.split(" ")
    var ct = 0
    var i = 0
    while (i < words.length) {
      val word = words(i)
      if ct + word.length + 1 > width then
        lines.append(line.toString)
        line.clear()
        ct = 0
      else
        line.append(" " + word)
      i += 1
      ct += word.length + 1

    }
    if line.nonEmpty then lines.append(line.toString)

    lines.toIndexedSeq
  }

  def getScrolledOutput(cursor:Int):IndexedSeq[ConsoleLine] = {
    if output.length <= visLines then
      output
    else
      output.slice(output.length - visLines - cursor, output.length - cursor)

  }

  def update:ConsoleOutput = {
    val c = cursor + Controls.chooseJust(Controls.Down, Controls.Up, -1, 1, 0)
    val cursorMin = 0
    val cursorMax = output.length - visLines
    val newCursor = if output.length <= visLines then 0 else GleanyMath.clamp(c, cursorMin, cursorMax)
    copy(cursor=newCursor)
  }

  def draw(pos:Cell)(tr:TileRenderer):TileRenderer =

    val cursorDebug = ("Cursor: " + cursor).zipWithIndex.map{ (c,i) =>
      ((i, 0), CP437.unicodeToCode(c).mkTile(Color.Black, Color.Red))
    } |++| (pos |+| (-2,-2))


    val lines = getScrolledOutput(cursor)
    val tiles = lines.zipWithIndex.flatMap({ case ((cmd, color), j) =>
      cmd.zipWithIndex.map((c, i) =>
        ((i, j) |+| pos, CP437.unicodeToCode(c).mkTile(Color.Black, color))
      )})
    tr <++ tiles <++ cursorDebug

}
