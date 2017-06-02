package graphGenerator

import collection.JavaConverters._
import scalax.collection.Graph
import scalax.collection.edge.LkDiEdge
import util.Model.GraphGenerator
import org.apache.jena.util.FileManager
import org.apache.jena.rdf.model.ModelFactory


/**
  * Created by chris on 02/06/17.
  */
class GraphFromRDF (val source: String) extends GraphGenerator {

	override def get: Graph[String, LkDiEdge] = {
		val in = FileManager.get().open(source)
		val model = ModelFactory.createDefaultModel()
		model.read(in, null, "TTL")
		val statements = model.listStatements().asScala

		val edges = statements.map(stmt =>
			LkDiEdge(stmt.getSubject.toString, stmt.getObject.toString) (stmt.getPredicate.toString)
		).toList

		Graph.from(List(""), edges)
	}

	def getExtension(filename: String): String = {
		val extension = filename
		"wat"
	}

}

object GraphFromRDF{

	def main(args: Array[String]) {

		val GeneratorFromRDF = new GraphFromRDF(args(0))
		val graph = GeneratorFromRDF.get

		println(graph.edges.size)
		println(graph.nodes.size)
		graph.get("http://dbpedia.org/resource/New_Jersey_General_Assembly").edges.foreach(println)
	}

}


