package scalanet.protocol

/** Message protocol between client and server */
object ClientServerProtocol {
  case class Ack(seqNumber: Int)
  case class Packet(seqNumber: Int)
}
