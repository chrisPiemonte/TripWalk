package sequenceGenerator

import graphGenerator.GraphFromRDF
import util.Model.SequenceGenerator

import scala.annotation.tailrec
import scala.util.Random
import scalax.collection.Graph
import scalax.collection.edge.LkDiEdge

/**
  * Created by chris on 03/06/17.
  */
class RandomWalker (val graph: Graph[String, LkDiEdge], val depth: Int, val numWalks: Int) extends SequenceGenerator {

	override def get: Seq[Seq[String]] = {
		Seq(Seq(""))
	}


	def randomWalk(node: Graph[String, LkDiEdge]#NodeT, depth: Int): Seq[String] = {

		@tailrec
		def randomWalkRec(node: Graph[String, LkDiEdge]#NodeT, depth: Int, acc: Seq[String]): Seq[String] = {
			if (acc.size < depth) {
				val source = node.value
				val someEdge = getRandomOutEdge(node)
				someEdge match {
					case Some(actualEdge) =>
						val arcLabel = actualEdge.label.toString
						randomWalkRec(actualEdge.to, depth, acc :+ source :+ arcLabel)
					case None => acc :+ source
				}
			} else acc
		}

		randomWalkRec(node, depth, Seq())
	}


	def getRandomOutEdge(node: Graph[String, LkDiEdge]#NodeT): Option[Graph[String, LkDiEdge]#EdgeT] = {
		if (node.outDegree > 0) {
			val outEdges = node.edges
				.filter(_.from equals node)
				.toVector
			val randomIndex = new Random().nextInt(node.outDegree)
			val randomEdge: Graph[String, LkDiEdge]#EdgeT = outEdges(randomIndex).value
			Some(randomEdge)
		} else {
			None
		}
	}


}

object RandomWalker {

	def main(args: Array[String]) {
		val GeneratorFromRDF = new GraphFromRDF(args(0))
		val graph = GeneratorFromRDF.get

		val RanWalker = new RandomWalker(graph, 5, 5)

		val node = graph.get("http://dbpedia.org/resource/Isaac_Asimov")
		val walk = RanWalker.randomWalk(node, 5)

		println(walk.size)
		println(walk)
	}

}
