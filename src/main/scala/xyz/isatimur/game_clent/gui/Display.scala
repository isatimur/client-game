package xyz.isatimur.game_clent.gui

import akka.stream.scaladsl.Sink
import xyz.isatimur.game_clent.domain.Player

import scalafx.application.Platform
import scalafx.scene.layout.{AnchorPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.text.Text

/**
  * ${CLASS_NAME}.
  * Created at 12/18/2017 1:44 PM by
  *
  * @author Timur Isachenko
  *
  */
class Display() {
  private val PlayerRadius = 100
  private val Dimensions = 6
  private val ScreenSize = PlayerRadius * Dimensions
  val panel = new AnchorPane {
    minWidth = ScreenSize
    minHeight = ScreenSize
  }

  def sink = Sink.foreach[List[Player]] { playerPositions =>
    val playersShapes = playerPositions.map(player => {
      new StackPane {
        minWidth = ScreenSize
        minHeight = ScreenSize
        layoutX = player.position.x * PlayerRadius
        layoutY = player.position.y * PlayerRadius
        prefHeight = PlayerRadius
        prefWidth = PlayerRadius
        val circlePlayer = new Circle {
          radius = PlayerRadius * 0.5
          fill = getColorForPlayer(player.name)
        }
        val textOnCircle = new Text {
          text = player.name
        }
        children = Seq(circlePlayer, textOnCircle)
        def getColorForPlayer(name: String) = {
          val r = 55 + math.abs(("r" + name).hashCode) % 200
          val g = 55 + math.abs(("g" + name).hashCode) % 200
          val b = 55 + math.abs(("b" + name).hashCode) % 200
          Color.rgb(r, g, b)
        }
      }
    })
    Platform.runLater({
      panel.children = playersShapes
      panel.requestLayout()
    })
  }

}
