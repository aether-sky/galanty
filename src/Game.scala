package sky.aether

import com.deweyvm.gleany.{Glean, GleanyGame, GleanyInitializer}
import sky.aether.input.{Controls, GalantyControls}

import java.util.concurrent.{Callable, Executors, TimeUnit}

object Game {
  var t = 0
  var frame = 0
  val Cols = 64 + 32
  val Rows = 48
  val TileSize = 16
  var Transitioning = false
}

class Game(initializer:GleanyInitializer) extends GleanyGame(initializer) {
  private lazy val engine = new Engine()
  override def update(): Unit = {
    Controls.update()
    engine.update()
    Game.t += 1
  }

  override def draw(): Unit = {
    engine.draw()
  }

  override def resize(width: Int, height: Int): Unit = {
    Glean.y.settings.setWindowSize(width, height)
  }

  override def dispose(): Unit = {
    val executor = Executors.newSingleThreadExecutor()
    executor.invokeAll(java.util.Arrays.asList(new Callable[Unit] {
      override def call(): Unit = ()
    }), 2, TimeUnit.SECONDS)
    executor.shutdown()
  }
}
