package sequenceGenerator

import java.io.File
import java.util.Date

import graphGenerator.GraphFromRDF
import util.Model.SequenceGenerator
import util.Writer.WalksWriter

import scala.annotation.tailrec
import scala.util.Random
import scalax.collection.Graph
import scalax.collection.edge.LkDiEdge

/**
  * Created by chris on 03/06/17.
  */
class RandomWalker (val graph: Graph[String, LkDiEdge], val depth: Int, val numWalks: Int) extends SequenceGenerator {

	val random = new Random

	override def get: Seq[Seq[String]] = {
		for {
			node <- graph.nodes.toList
			i <- 0 until numWalks
		} yield randomWalk(node)
	}

	@tailrec
	private def randomWalkRec(node: Graph[String, LkDiEdge]#NodeT, acc: List[String]): Seq[String] = {
		if (acc.size < depth) {
			getRandomOutEdge(node) match {
				case Some(edge) =>
					randomWalkRec( edge.to, edge.label.toString :: node.value :: acc )
				case _ =>
					node.value :: acc
			}
		} else
			acc
	}

	def randomWalk(node: Graph[String, LkDiEdge]#NodeT): Seq[String] = {
		randomWalkRec(node, List()).reverse
	}


	def getRandomOutEdge(node: Graph[String, LkDiEdge]#NodeT): Option[Graph[String, LkDiEdge]#EdgeT] = {
		if(node.outDegree > 0)
			Some(node.outgoing.toList( random.nextInt(node.outDegree) ))
		else
			None
	}


}

object RandomWalker {

	def main(args: Array[String]) {
		val sourceFile = args(0)

		val GraphGen = new GraphFromRDF(sourceFile)
		val graph = GraphGen.get
		val RanWalker = new RandomWalker(graph, 10, 10)

		val node = graph.get("http://dbpedia.org/resource/Scottish_music")

		val startTime = new Date().getTime
		println("Random Walks generation starting at " + startTime)

		val walks = RanWalker.get

		val endTime = new Date().getTime
		val totSec = (endTime - startTime) / 1000.0
		println("Random Walks generation completed in " + totSec + " seconds")

		println(walks.size)
		// walks.foreach(println)


		val outputFile = new File(sourceFile).getParent + "/embedding_.txt"

		val Writer = new WalksWriter(outputFile)
		Writer(walks)
	}

}
