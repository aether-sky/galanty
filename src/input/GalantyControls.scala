package sky.aether
package input

import com.deweyvm.gleany.saving.{ControlName, ControlNameCollection}

class GalantyControls(descriptor: String) extends ControlName {
  override def name: String = descriptor
}

object GalantyControls extends ControlNameCollection[GalantyControls] {
  def fromString(string: String): Option[GalantyControls] = None
  def makeJoypadDefault: Map[String,String] = Map()
  def makeKeyboardDefault: Map[String,java.lang.Float] = Map()
  def values: Seq[GalantyControls] = Seq()
}

