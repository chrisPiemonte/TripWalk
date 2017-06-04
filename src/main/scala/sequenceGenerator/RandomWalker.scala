package sequenceGenerator

import java.util.Date

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
		for {
			node <- graph.nodes.toList
			i <- 0 until numWalks
		} yield randomWalk(node, depth)

		/*for (node <- graph.nodes.toList; i <- 0 until numWalks)
			yield randomWalk(node, depth)*/
	}


	def randomWalk(node: Graph[String, LkDiEdge]#NodeT, depth: Int): Seq[String] = {
		@tailrec
		def randomWalkRec(node: Graph[String, LkDiEdge]#NodeT, depth: Int, acc: Seq[String]): Seq[String] = {
			if (acc.size < depth) {
				val source = node.value
				val optionEdge = getRandomOutEdge(node)
				optionEdge match {
					case Some(actualEdge) =>
						val arcLabel = actualEdge.label.toString
						randomWalkRec(actualEdge.to, depth, acc :+ source :+ arcLabel)
					case None =>
						acc :+ source
				}
			} else
				acc
		}
		// println("starting on node: " + node.hashCode() + " " + node.value)
		randomWalkRec(node, depth, Seq())
	}


	def getRandomOutEdge(node: Graph[String, LkDiEdge]#NodeT): Option[Graph[String, LkDiEdge]#EdgeT] = {
		if (node.outDegree > 0) {
			val outEdges = node.edges
				.filter(_.from equals node)
				.toVector
			val randomIndex = new Random().nextInt(node.outDegree)
			val randomEdge = outEdges(randomIndex).value
			Some(randomEdge)
		} else
			None
	}


}

object RandomWalker {

	def main(args: Array[String]) {
		val GeneratorFromRDF = new GraphFromRDF(args(0))
		val graph = GeneratorFromRDF.get

		val RanWalker = new RandomWalker(graph, 3, 1)
/*
		val node = graph.get("http://dbpedia.org/resource/Isaac_Asimov")
		// val walk = RanWalker.randomWalk(node, 4)

		val startTime = new Date().getTime
		println("Random Walks generation starting at "+ startTime)

		val walks = RanWalker.get

		val endTime = new Date().getTime
		val totSec = (endTime-startTime) / 1000
		println("Random Walks generation completed in " + totSec + " seconds")

		println(walks.size)
		println(walks)*/
		var l = scala.collection.mutable.ArraySeq.empty[Seq[String]]

		for (node <- graph.nodes.toList; i <- 0 until 3 ) {

			l :+ RanWalker.randomWalk(node, 3)
		}

		println(l.size)
		// g.foreach(println)
	}

}
