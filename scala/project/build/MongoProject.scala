import sbt._

class MongoProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val scalatraVersion = "2.0.0.M2"
  val scalatra = "org.scalatra" %% "scalatra" % scalatraVersion
  val servletApi = "org.mortbay.jetty" % "servlet-api" % "2.5-20081211" % "provided"
  val liftJson = "net.liftweb" %% "lift-json" % "2.2"
  val scalatest = "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test"
  val slf4jBinding = "ch.qos.logback" % "logback-classic" % "0.9.25" % "runtime"
  override def testClasspath = super.testClasspath +++ buildCompilerJar
  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val fuseSourceSnapshots = "FuseSource Snapshot Repository" at "http://repo.fusesource.com/nexus/content/repositories/snapshots"
}
