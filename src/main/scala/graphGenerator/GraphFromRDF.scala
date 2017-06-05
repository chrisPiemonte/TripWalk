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
		val readerType = getExtension(source) match {
			case Some("ttl") => "TTL"
			case Some("nt") => "NT"
			case _ => "RDF/XML"
		}

		model.read(in, null, readerType)
		val statements = model.listStatements().asScala

		val edges = statements.map(stmt =>
			LkDiEdge(stmt.getSubject.toString, stmt.getObject.toString) (stmt.getPredicate.toString)
		).toList

		Graph.from(List(), edges)
	}

	def getExtension(filename: String): Option[String] = {
		if (filename contains ".")
			Some(filename.substring( filename.lastIndexOf(".") + 1))
		else
			None
	}

}


