package xyz.isatimur.game_clent

import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, OverflowStrategy}
import xyz.isatimur.game_clent.gui.{Display, GUI, KeyBoardHandler}

import scala.io.StdIn

/**
  * ${CLASS_NAME}.
  * Created at 12/18/2017 1:41 PM by
  *
  * @author Timur Isachenko
  *
  */
object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val name = StdIn.readLine();

    val client = new Client(name)
    val display = new Display()
    val input = Source.actorRef[String](5, OverflowStrategy.dropNew)
    val output = display.sink

    val ((inputMat, result), outputMat) = client.run(input, output)
    val keyBoardHandler = new KeyBoardHandler(inputMat)

    new GUI(keyBoardHandler, display).main(args)
  }
}
