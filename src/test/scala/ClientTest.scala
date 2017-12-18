
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}
import akka.stream.scaladsl.{Keep, Source}
import akka.stream.testkit.scaladsl.TestSink
import akka.stream.{ActorMaterializer, OverflowStrategy}
import org.scalatest.{FunSuite, Matchers}
import xyz.isatimur.game_clent.Client
import xyz.isatimur.game_clent.domain.{Player, Position}

/**
  * ${CLASS_NAME}.
  * Created at 12/14/2017 2:41 PM by
  *
  * @author Timur Isachenko
  *
  */
class ClientTest extends FunSuite with Matchers {

  test("should be able to register") {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()


    val testSink = TestSink.probe[Message]

    val outgoing = Source.empty[Message]

    val webSocketFlow = Http().webSocketClientFlow(WebSocketRequest("ws://localhost:8080/?playerName=jacob"))

    val (upgradeResponse, testProbe) =
      outgoing
        .viaMat(webSocketFlow)(Keep.right) // keep the materialized Future[WebSocketUpgradeResponse]
        .toMat(testSink)(Keep.both) // also keep the Future[Done]
        .run()

    testProbe.request(1);
    testProbe.expectNext(TextMessage("[{\"name\":\"jacob\",\"position\":{\"x\":0,\"y\":0}}]"));
  }

  test("should be able to move player") {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    val client = new Client("Jacob")
    val input = Source.actorRef[String](5, OverflowStrategy.dropNew)
    val output = TestSink.probe[List[Player]]
    val ((inputMat, result), outputMat) = client.run(input, output)

    //    test send "up"
    inputMat ! "up"

    outputMat.request(2)
    outputMat.expectNext(List(Player("Jacob", Position(0, 0))))
    outputMat.expectNext(List(Player("Jacob", Position(0, 1))))
  }
}














