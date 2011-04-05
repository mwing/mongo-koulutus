import sbt._

class MongoProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val scalatraVersion = "2.0.0.M3"
  val scalatra = "org.scalatra" %% "scalatra" % scalatraVersion
  val servletApi = "org.mortbay.jetty" % "servlet-api" % "2.5-20081211" % "provided"
  
  // Json
  val liftJson = "net.liftweb" %% "lift-json" % "2.3-M1"
  // override Rogue dependencies to achieve ReplicaSet failover functionality
  val liftMongoRecord = "net.liftweb" %% "lift-mongodb-record" % "2.3-M1" withSources()
  val liftMongo = "net.liftweb" %% "lift-mongodb" % "2.3-M1" withSources()

  val slf4jBinding = "ch.qos.logback" % "logback-classic" % "0.9.28" % "runtime"

//  val casbah = "com.mongodb.casbah" % "casbah_2.8.0" % "2.1.1"
  val rogue = "com.foursquare" % "rogue_2.8.0" % "1.0.6" withSources()
  val scalatest = "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test"

  override def testClasspath = super.testClasspath +++ buildCompilerJar

  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val fuseSourceSnapshots = "FuseSource Snapshot Repository" at "http://repo.fusesource.com/nexus/content/repositories/snapshots"
  val stRels = "scalatools-releases" at "http://scala-tools.org/repo-releases"
  val stSnaps = "scalatools-snapshots" at "http://scala-tools.org/repo-snapshots"

//  val bumRels = "bum-releases" at "http://repo.bumnetworks.com/releases"
//  val bumSnaps = "bum-snapshots" at "http://repo.bumnetworks.com/snapshots"
}
