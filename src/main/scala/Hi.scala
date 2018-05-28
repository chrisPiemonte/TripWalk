import org.apache.jena.rdf.model.{ModelFactory, Statement}
import org.apache.jena.util.FileManager

import collection.JavaConverters._

/**
  * Created by chris on 27/05/17.
  */
object Hi {

  def main(args: Array[String]) {

    val x = for{ i <- List.range(0, 10) } yield i
    println(x)

    val inputFileName = args(0)
    println(inputFileName)

    val in = FileManager.get().open(inputFileName)
    val model = ModelFactory.createDefaultModel()
    model.read(in, null, "TTL")
    model.listStatements()

    val iter = model.listStatements().asScala
    iter.foreach(print)
    println("\n\nciao\n\n")
    iter.foreach(print)
    /*
    println("\n\nstarting")
    while (iter.hasNext){
      val stmt = iter.nextStatement()
      val subject = stmt.getSubject
      val predicate = stmt.getPredicate
      val obj = stmt.getObject

      print(subject.toString)
      print(" " + predicate.toString + " ")
      if(obj.isInstanceOf[Resource]){
        print(obj.toString())
      } else {
        print(" \"" + obj.toString + "\"")
      }

      println(" .")

    }
    */

    // val iterScala = iter.asScala

    iter.map((x: Statement) => {
      val s = x.getSubject.toString
      val p = x.getPredicate.toString
      val o = x.getObject.toString
            s + " " + p +  " " + o
    }).foreach(println)

    println(model.size())
  }

}
