package xyz.isatimur.game_clent.gui

import akka.actor.ActorRef

import scalafx.scene.input.{KeyCode, KeyEvent}

/**
  * ${CLASS_NAME}.
  * Created at 12/18/2017 1:44 PM by
  *
  * @author Timur Isachenko
  *
  */
class KeyBoardHandler(keyboardEventsReceiver: ActorRef) {
  def handle(keyEvent: KeyEvent) = keyEvent.code match {
    case KeyCode.Up => keyboardEventsReceiver ! "down" //scalafx coordinates are reversed
    case KeyCode.Down => keyboardEventsReceiver ! "up"
    case KeyCode.Left => keyboardEventsReceiver ! "left"
    case KeyCode.Right => keyboardEventsReceiver ! "right"
    case _ =>
  }
}
