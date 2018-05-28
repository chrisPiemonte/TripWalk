package util

import java.io.{File, PrintWriter}

/**
  * Created by chris on 05/06/17.
  */
object Writer {

  class WalksWriter(outputPath: String){

    val file = new File(outputPath)
    val out  = new PrintWriter(file, "UTF-8")

    def apply(walks: Seq[Seq[String]]): Unit = {
      walks
        .filter(_.size > 1)
        .foreach( walk => out.println(walkToString(walk)) )
      out.close()
    }

    private def walkToString(walk: Seq[String]): String = {
      walk.mkString(" ")
    }

    def close(): Unit =
      out.close()

  }

}
