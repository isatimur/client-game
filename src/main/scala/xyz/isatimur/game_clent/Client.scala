package xyz.isatimur.game_clent

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{TextMessage, WebSocketRequest}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}
import spray.json.DefaultJsonProtocol
import xyz.isatimur.game_clent.domain.{Player, Position}

/**
  * ${CLASS_NAME}.
  * Created at 12/18/2017 1:41 PM by
  *
  * @author Timur Isachenko
  *
  */
class Client(playerName: String)(implicit val actorSystem: ActorSystem, implicit val actorMaterializer: ActorMaterializer) extends DefaultJsonProtocol {

  import spray.json._

  implicit val positionFormat = jsonFormat2(Position)
  implicit val playerFormat = jsonFormat2(Player)

  val webSocketFlow = Http().webSocketClientFlow(WebSocketRequest(s"ws://localhost:8080/?playerName=$playerName")).collect {
    case TextMessage.Strict(strMsg) => strMsg.parseJson.convertTo[List[Player]]
  }

  def run[M1, M2](input: Source[String, M1], output: Sink[List[Player], M2]) = {
    input.map(direction => TextMessage(direction))
      .viaMat(webSocketFlow)(Keep.both) // keep the materialized Future[WebSocketUpgradeResponse]
      .toMat(output)(Keep.both) // also keep the Future[Done]
      .run()

  }

}
