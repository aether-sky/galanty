package sky.aether
package network

import java.io.PrintStream
import java.net.ServerSocket
import scala.io.BufferedSource

class Server {
  val server = new ServerSocket(9999)
  while (true) {
    val s = server.accept()
    val in = new BufferedSource(s.getInputStream()).getLines()
    val out = new PrintStream(s.getOutputStream())

    out.println(in.next())
    out.flush()
    s.close()
  }
}
