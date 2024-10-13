package sky.aether

import com.deweyvm.gleany.{GleanyConfig, GleanyGame, GleanyInitializer}
import com.deweyvm.gleany.data.Point2i
import com.deweyvm.gleany.files.PathResolver
import com.deweyvm.gleany.saving.{SettingDefaults, Settings}
import in.dogue.antiqua.graphics.Tile
import sky.aether.input.GalantyControls


object Main {
  def main(args: Array[String]): Unit = {
    val settings = new Settings(GalantyControls, new SettingDefaults() {
      val SfxVolume: Float = 0.2f
      val MusicVolume: Float = 0.2f
      val WindowSize: Point2i = Point2i(Game.TileSize*Game.Cols, Game.TileSize*Game.Rows)
      val DisplayMode: Int = 0
    }, false)
    val iconPaths = Seq()
    val initializer = GleanyInitializer(new PathResolver("font", "sprites", "sfx", "music", "data", "shaders", "maps"),
      settings)
    val config = GleanyConfig(settings, "Galanty", iconPaths)

    GleanyGame.runGame(config, new Game(initializer))
  }
}