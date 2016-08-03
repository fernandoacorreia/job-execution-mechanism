import scala.util.Try

class ProcessTracker(command: Seq[String], process: Process) {
  def exitValue: Try[Int] = Try(process.exitValue)

  def isAlive: Boolean = process.isAlive

  def output: String = {
    val inputStream = process.getInputStream
    scala.io.Source.fromInputStream(inputStream).mkString
  }

  override def toString: String = {
    s"command=${command.mkString(" ")} isAlive=$isAlive exitValue=$exitValue"
  }
}
