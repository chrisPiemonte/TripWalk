name := "TripWalk"

version := "1.0"

scalaVersion := "2.11.8"


assemblyOption in assembly :=
	(assemblyOption in assembly).value.copy(includeScala = true)