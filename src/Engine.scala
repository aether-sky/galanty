package sky.aether

import modes.{GameMode, Mode, TitleMode}

import com.deweyvm.gleany.{AssetLoader, GleanyGame}
import in.dogue.antiqua.graphics.{Renderer, TileRenderer, Tileset}
import sky.aether.console.TextInput
import sky.aether.input.Controls

import scala.util.Random


class Engine {
  val cols = Game.Cols
  val rows = Game.Rows
  val tsize = Game.TileSize
  val rand = new Random(0)
  TextInput.init()
  val m:Mode = {
    TitleMode.mkTitleMode.toMode
    //GameMode.mkGameMode.toMode
  }
  var mode:Mode = m
  val ts = Tileset(16, 16, tsize, tsize, AssetLoader.loadTexture("16x16"))
  val r = new Renderer(cols*tsize, rows*tsize, 1, ts)
  def update():Unit = {
    if (Controls.Escape.justPressed) {
      GleanyGame.exit()
    }
    mode = mode.update
  }
  def draw(): Unit = {
    val tr = TileRenderer.create(cols, rows)
    r.render(tr <+< mode.draw)
    ()
  }

}