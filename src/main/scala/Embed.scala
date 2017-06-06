import java.io.File
import java.util.Date

import graphGenerator.GraphFromRDF
import sequenceGenerator.RandomWalker
import util.Writer.WalksWriter

/**
  * Created by chris on 06/06/17.
  */
object Embed {

	def main(args: Array[String]) {

		args match {
			case Array(sourceFile, depth, numWalks) =>
				val GraphGen = new GraphFromRDF(sourceFile)
				val graph = GraphGen.get
				val RanWalker = new RandomWalker(graph, depth.toInt, numWalks.toInt)

				val startTime = new Date().getTime
				println("Starting Random Walks generation . . .")

				val walks = RanWalker.get

				val endTime = new Date().getTime
				val totSec = (endTime - startTime) / 1000.0
				println("Completed in " + totSec + " seconds")

				val outputFile = new File(sourceFile).getParent + "/embedding_" +
					sourceFile.substring( sourceFile.lastIndexOf("/") + 1) + ".txt"

				println("Writing on file " + outputFile + " . . .")
				val Writer = new WalksWriter(outputFile)
				Writer(walks)
				println("Done")

			case _ =>
				Console.err.println(s"wrong parameters for: ${args.mkString(" ")}")
				val string = """to run the jar do: java -cp name.jar Embed <sourceFile> <depth> <numWalks>
					| where:
				    | <sourceFile> : file containing RDF triples
					| <depth> : length of each random walk (e.g. 5)
					| <numWalks> : number of walks generated for each node
					 """

				Console.err.println(string)
		}

	}
	
}
