package scalanet.simulator

import akka.actor.{ ActorSystem, Props }
import scala.collection.mutable
import scala.Console.err
import scalanet.protocol.AppClientProtocol._
import scalanet.system._

/** Stop and wait sliding window simulator
  *
  * Simplest form of sliding window protocol in networking.
  * Send and receive window are both set to 1. This means
  * only one packet is sent at a time, and the next packet is
  * not sent until the prior packet was acknowledged.
  */
object StopAndWait {
  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      err.println("Usage: sbt 'run-main scalanet.simulator.StopAndWait [probability of lost packets]" +
                  "[# of seconds for timeout] [# of packets to send]'")
      return
    }
    
    val network = ActorSystem("scalanet")
    val server = network.actorOf(Props(new Server(args(0).toDouble)))
    val client = network.actorOf(Props(new StopAndWaitClient(args(0).toDouble, args(1).toInt, args(2).toInt, server)))

    client ! Start
  }
}
