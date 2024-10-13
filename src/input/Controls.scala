package sky.aether
package input

import in.dogue.antiqua.Antiqua
import Antiqua.*
import com.badlogic.gdx.{Gdx, Input}
import com.deweyvm.gleany.input.{AxisControl, Control, JoypadButton}
import com.deweyvm.gleany.input.triggers.{JoypadTrigger, KeyboardTrigger, Trigger, TriggerAggregate}

import scala.collection.mutable.ArrayBuffer
//Game.Transitioning
class MouseTrigger(val button: Int) extends Trigger:
  override def isPressed: Boolean = {
    Gdx.input.isButtonPressed(button) && !Game.Transitioning
  }

object Controls {
  val All = ArrayBuffer[Control[Boolean]]()
  val Left = makePr(Input.Keys.LEFT, JoypadButton("5"), None)
  val Right = makePr(Input.Keys.RIGHT, JoypadButton("6"), None)
  val Up = makePr(Input.Keys.UP, JoypadButton("6"), None)
  val Down = makePr(Input.Keys.DOWN, JoypadButton("6"), None)
  val Escape = makeKb(Input.Keys.ESCAPE)
  val AxisX = new AxisControl(Left, Right)
  val AxisY = new AxisControl(Up, Down)
  val Click = makeMouse(0)

  def mouseCell:Cell = {
    (Gdx.input.getX/Game.TileSize, Gdx.input.getY/Game.TileSize)
  }

  def mousePos:(Int,Int) = {
    (Gdx.input.getX, Gdx.input.getY)
  }

  def register(c:Control[Boolean]):Control[Boolean] = {
    All += c
    c
  }

  def makeKb(key:Int): Control[Boolean] = {
    val result = new TriggerAggregate(Seq(new KeyboardTrigger(key)))
    register(result)
  }

  def makeMouse(mouse:Int): Control[Boolean] = {
    val mouseTrigger = new MouseTrigger(mouse)
    val result = TriggerAggregate(Seq(mouseTrigger))
    register(result)
  }

  def makePr(key:Int, ctrl:JoypadButton, mouse:Option[Int]): Control[Boolean] = {
    val mouseTrigger: Seq[Option[MouseTrigger]] = Seq(mouse.map { i => new MouseTrigger(i)})
    val mt = mouseTrigger.flatten
    val result = new TriggerAggregate(mt ++ Seq(new KeyboardTrigger(key), new JoypadTrigger(ctrl)))
    register(result)
  }

  def choose[T](f:Control[Boolean]=>Boolean)(ctrl1: Control[Boolean], ctrl2: Control[Boolean], opt1: T, opt2: T, default: T): T =
    if f(ctrl1) then opt1
    else if f(ctrl2) then opt2
    else default

  def chooseJust[T](ctrl1: Control[Boolean], ctrl2: Control[Boolean], opt1: T, opt2: T, default: T): T = {
    if ctrl1.justPressed then opt1
    else if ctrl2.justPressed then opt2
    else default
  }

  def chooseIs[T](ctrl1: Control[Boolean], ctrl2: Control[Boolean], opt1: T, opt2: T, default: T): T = {
    if ctrl1.isPressed then opt1
    else if ctrl2.isPressed then opt2
    else default
  }

  def update(): Unit = {
    All foreach (_.update())
  }
}