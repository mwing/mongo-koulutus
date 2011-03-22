import sbt._

class MongoProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val scalatraVersion = "2.0.0.M3"
  val scalatra = "org.scalatra" %% "scalatra" % scalatraVersion
  val servletApi = "org.mortbay.jetty" % "servlet-api" % "2.5-20081211" % "provided"
  
  // Json
  val liftJson = "net.liftweb" %% "lift-json" % "2.2"
  
  // Casbah repos and dependency
  val bumRels = "bum-releases" at "http://repo.bumnetworks.com/releases" 
  val bumSnaps = "bum-snapshots" at "http://repo.bumnetworks.com/snapshots" 
  val casbah = "com.novus" % "casbah_2.8.0" % "1.0.8.5" 
  
  val scalatest = "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test"
  val slf4jBinding = "ch.qos.logback" % "logback-classic" % "0.9.25" % "runtime"
  override def testClasspath = super.testClasspath +++ buildCompilerJar
  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val fuseSourceSnapshots = "FuseSource Snapshot Repository" at "http://repo.fusesource.com/nexus/content/repositories/snapshots"
}
