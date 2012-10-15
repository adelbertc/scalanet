========
Scalanet
========
As it stands, this project provides a basic demonstration of how Scala and Akka can
be used to create a network simulator. This is all done within one process, making
it convenient to simulate networks - the ``Actor`` concurrency model lends itself
to naturally representing different machines in a computer network.

Already implemented is a simulation of a TCP client-server model communicating using
a stop-and-wait methodology, a simple variant of a sliding window protocol.
The stop-and-wait method has a send and receive window of 1, where the client will send
1 packet to the server and will not send another until the prior packet is properly
ACK'ed for.

The client and server are simulated by separate ``Actor`` instances. Lost packets are simulated 
probabilistically.

This project was written for entry in the 
Typesafe Developer Contest (http://www.typesafe.com/resources/developer-contest).

Compilation
-----------
The project is written with SBT 0.12.1, with the only dependencies being Scala 2.9.2 and Akka 2.0.3.
It can be compiled simply with ``sbt compile`` and run with ``sbt run``.

License
-------
This project is licensed under Apache2, please see the LICENSE for more details.