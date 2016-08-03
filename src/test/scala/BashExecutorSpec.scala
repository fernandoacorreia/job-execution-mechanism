import org.specs2.mutable.Specification

import scala.annotation.tailrec

class BashExecutorSpec extends Specification {
  "executing Bash commands" should {
    "succeed for valid command" in {
      val bashCommand = Seq("true")
      val bashExecutor = new BashExecutor
      val process = bashExecutor.executeBashCommand(bashCommand)
      getExitCode(process) must_== 0
    }

    "fail for invalid command" in {
      val bashCommand = Seq("false")
      val bashExecutor = new BashExecutor
      val process = bashExecutor.executeBashCommand(bashCommand)
      getExitCode(process) must_== 1
    }
  }

  private def getExitCode(process: ProcessTracker): Int = {
    waitForProcessToFinish(process)
    process.exitValue.getOrElse {
      throw new RuntimeException(s"Process did not finish: $process")
    }
  }

  @tailrec
  private def waitForProcessToFinish(process: ProcessTracker, attempt: Int = 1): Unit = {
    val maxAttempts = 10
    if (process.isAlive || process.exitValue.isFailure) {
      if (attempt >= maxAttempts) {
        throw new RuntimeException(s"Too many attempts waiting for process to finish: $process")
      } else {
        Thread.sleep(100)
        waitForProcessToFinish(process, attempt + 1)
      }
    }
  }
}
