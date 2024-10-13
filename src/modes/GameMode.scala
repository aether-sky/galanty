package sky.aether
package modes

import in.dogue.antiqua.graphics.{Border, Tile, TileRenderer}
import in.dogue.antiqua.data.CP437
import in.dogue.antiqua.Antiqua
import Antiqua.*
import com.badlogic.gdx.{Gdx, InputAdapter}
import com.deweyvm.gleany.graphics.Color
import sky.aether.console.Console
import sky.aether.logic.CharRecord
import sky.aether.procgen.Tower


object GameMode {
  def mkGameMode(c:CharRecord):Mode = {

    GameMode(Console.mkConsole).toMode
  }
}

class GameMode(console:Console) {

  def update: Mode =
    GameMode(console.update).toMode

  def draw(tr:TileRenderer): TileRenderer = {
    tr <+< console.draw(10,10)
  }

  def toMode: Mode = Mode[GameMode](_.update, _.draw, this)
}
