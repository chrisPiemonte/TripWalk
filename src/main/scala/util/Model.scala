package util

import scalax.collection.Graph
import scalax.collection.edge.LkDiEdge

/**
  * Created by chris on 02/06/17.
  */
object Model {

	// type uri = String

	trait GraphGenerator {
		val source: String
		def get: Graph[String, LkDiEdge]
	}
	
}
