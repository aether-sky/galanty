package sky.aether
package console

import com.badlogic.gdx.{Gdx, InputAdapter}
import com.deweyvm.gleany.graphics.Color
import in.dogue.antiqua.Antiqua.*
import in.dogue.antiqua.data.CP437
import in.dogue.antiqua.graphics.TileRenderer

object TextInput {
  private var initialized = false
  private val buffer = StringBuilder()
  def acceptString:Option[String] = {
    if buffer.nonEmpty && buffer(buffer.length-1) == 10 then
      val res = buffer.slice(0, buffer.length-1).toString
      buffer.clear()
      if res.strip().nonEmpty then
        Some(res)
      else
        None
    else
      None

  }
  def init():Unit = {
    Gdx.input.setInputProcessor(new InputAdapter() {
      override def keyTyped(character: Char): Boolean = {
        if character == 8 then
          if buffer.nonEmpty then
            buffer.deleteCharAt(buffer.length-1)
          else
            ()
        else
          buffer.append(character)
        true
      }
    })
    initialized = true
  }

}

class TextInput() {
  def length:Int = TextInput.buffer.length
  def update:(TextInput,Option[String]) = {
    (this, TextInput.acceptString)
  }
  def draw(pos:Cell)(tr:TileRenderer):TileRenderer = {
    val str = TextInput.buffer.zipWithIndex.map( (c,i) =>
      (pos |+ i, CP437.unicodeToCode(c).mkTile(Color.Black,Color.White))
    ).toSeq

    tr <++ str
  }
}