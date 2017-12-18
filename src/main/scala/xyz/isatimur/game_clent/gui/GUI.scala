package xyz.isatimur.game_clent.gui

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.input.KeyEvent

/**
  * ${CLASS_NAME}.
  * Created at 12/18/2017 1:45 PM by
  *
  * @author Timur Isachenko
  *
  */
class GUI(keyBoardHandler: KeyBoardHandler, display: Display) extends JFXApp {

  import scalafx.Includes._

  stage = new JFXApp.PrimaryStage {
    title.value = "Client"
    scene = new Scene {
      content = display.panel
      onKeyPressed = (ev: KeyEvent) => keyBoardHandler.handle(ev)
    }
  }
}