package scalanet.system

import akka.actor.Actor
import scala.util.Random
import scalanet.protocol.ClientServerProtocol._

/** Class for "Server" in a client-server architecture
  *
  * @param dropPacketProb probability of packets being "dropped"
  */
class Server(dropPacketProb: Double) extends Actor {
  var expectedPacketNumber = 0
  def receive = {
    case Packet(seqNumber) => 
      if (seqNumber == expectedPacketNumber) {
        // ACK for next expected packet, needed for other sliding window protocol implementations
        // Doesn't really matter for stop and wait
        if (Random.nextDouble() > dropPacketProb)
          sender ! Ack(expectedPacketNumber + 1)
        expectedPacketNumber += 1
      } else {
        if (Random.nextDouble() > dropPacketProb) sender ! Ack(expectedPacketNumber) 
      }
  }
}
