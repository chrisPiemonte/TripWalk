package util

import scalax.collection.Graph
import scalax.collection.edge.LkDiEdge

/**
  * Created by chris on 02/06/17.
  */
object Model {

  trait GraphGenerator {
    val source: String
    def get: Graph[String, LkDiEdge]
  }

  trait SequenceGenerator {
    val depth: Int
    val numWalks: Int
    val graph: Graph[String, LkDiEdge]
    def get: Seq[Seq[String]]
  }

}
