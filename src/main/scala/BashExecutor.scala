import scala.collection.JavaConversions._

class BashExecutor {
  def executeBashCommand(bashCommand: Seq[String]): ProcessTracker = {
    val command = prepareCommand(bashCommand)
    val process = startProcess(command)
    new ProcessTracker(command, process)
  }

  private[this] def prepareCommand(bashCommand: Seq[String]): Seq[String] = {
    val preparedBashCommand = s"${bashCommand.mkString(" ")}"
    Seq("/bin/bash", "-c", preparedBashCommand)
  }

  private def startProcess(command: Seq[String]): Process = {
    val processBuilder = new ProcessBuilder(command)
    processBuilder.redirectErrorStream(true)
    val process = processBuilder.start()
    process
  }
}
