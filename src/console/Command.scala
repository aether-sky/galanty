package sky.aether
package console

object Command {
  def parse(s:String):Command = {
    val words = s.split(" ")
    NotFound(words(0), words.slice(1, words.length).mkString(" "))
  }

  def process(c:Command):Seq[String] =
    c match {
      case NotFound(word, s) => Seq(s"Command $word not found",
        "Available commands:",
        "    stop",
        "    eat",
        "    bargain",
        "    rattle bars")
    }

  case class NotFound(word:String, rest:String) extends Command
}

trait Command

