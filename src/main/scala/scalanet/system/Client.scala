package scalanet.system

import akka.actor.{ Actor, ActorLogging, ActorRef }
import scalanet.protocol.ClientServerProtocol._

/** Abstract base class for "Client" in a client-server architecture
  *
  * @param dropPacketProb probability of packets being "dropped"
  * @param n number of packets to send
  * @param server ActorRef to the "Server" Actor instance
  */
abstract class Client(dropPacketProb: Double, n: Int, server: ActorRef) 
                      extends Actor with ActorLogging {
  val packets = Vector() ++ (0 until n).map(p => Packet(p))
  // sb keeps track of index position of `packets` that should be ACK'ed next
  // Done this way for other sliding window protocol implementations
  var sb = 0
}
