package scalanet.system

import akka.actor.{ ActorRef, ReceiveTimeout }
import akka.util.duration._
import scala.util.Random
import scalanet.protocol.AppClientProtocol._
import scalanet.protocol.ClientServerProtocol._

/** Abstract base class for "Client" in a client-server architecture
  *
  * @param dropPacketProb probability of packets being "dropped"
  * @param timeoutSeconds # of seconds for timeout (consider ACK not received)
  * @param n number of packets to send
  * @param server ActorRef to the "Server" Actor instance
  */
class StopAndWaitClient(dropPacketProb: Double, timeoutSeconds: Int, n: Int, server: ActorRef) 
                        extends Client(dropPacketProb, n, server) {

  // Awesomeness that makes timing out for stop and wait easy
  context.setReceiveTimeout(timeoutSeconds seconds)
  
  // Probabilistically sends a packet - simulates packet loss in a network
  def maybeSendPacket(): Unit = {
    val nextPacket = packets(sb)
    log.info("Sending packet #" + nextPacket.seqNumber)
    if (Random.nextDouble() > dropPacketProb) server ! nextPacket
  }

  def receive = {
    case Start => 
      maybeSendPacket()

    case Ack(seqNumber) => 
      log.info("Received ACK for packet #" + (sb))
      sb += 1
      if (sb == n) {
        log.info("All packets sent and received successfully, system shutting down.")
        context.system.shutdown()
      } else maybeSendPacket()

    case ReceiveTimeout =>
      log.info("Did not receive ACK for packet #" + sb + " within timeout period, re-sending...")
      maybeSendPacket()
  }
}
