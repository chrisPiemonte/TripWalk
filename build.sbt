name := "TripWalk"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= List(
	"org.apache.jena" % "apache-jena-libs" % "3.3.0",
	"org.scala-graph" %% "graph-core" % "1.11.3"
)

assemblyJarName in assembly := "TripWalk.jar"

assemblyOption in assembly :=
	(assemblyOption in assembly).value.copy(includeScala = true)