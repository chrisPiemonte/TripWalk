# Trip Walk <img src="https://raw.githubusercontent.com/chrisPiemonte/TripWalk/master/src/main/resources/tw.png" width="150">

Random Wlak generation in a RDF graph. It produces paths inside the graph (aka random walks) for each RDF resource. The `<numWalks>` parameter stands for how many random walks will be generated starting from each resource and the `<depth>` parameter is the length of each random walk. Each random walk starts and ends on a RDF resource, but relationships are included in between.

## Getting Started

1. Clone this repo:<pre>git clone https://github.com/chrisPiemonte/TripWalk.git </pre> <pre>cd TripWalk/</pre>

2. Run:<pre>sbt assembly</pre>

3. Run da jar:<pre>java -cp target/scala-2.11/TripWalk.jar Embed `<RDF file>` `<depth>` `<numWalks>` </pre>
