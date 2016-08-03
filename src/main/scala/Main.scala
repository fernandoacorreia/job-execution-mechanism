// 1) Write a mechanism for executing jobs.
//
// A job is defined by a list of strings containing a single Bash command to be executed.
// E.g. ("ls", "-l", "-a", "~").
//
// As a suggestion, in Java, use the ProcessBuilder class:
// https://docs.oracle.com/javase/8/docs/api/java/lang/ProcessBuilder.html

object Main {
  def main(args: Array[String]) {
    val jobs = Seq(
      Seq("echo", "hello"),
      Seq("ls", "/var"),
      Seq("ls", "/no-such-file")
    )
    val bashExecutor = new BashExecutor()
    executeJobs(jobs, bashExecutor)
  }

  private def executeJobs(jobs: Seq[Seq[String]], bashExecutor: BashExecutor): Unit = {
    val processes = jobs.map(bashExecutor.executeBashCommand)
    Thread.sleep(1000) // Give the processes some time to run.
    processes.foreach(displayProcess)
  }

  private def displayProcess(process: ProcessTracker): Unit = {
    println(process)
    println(process.output)
  }
}
