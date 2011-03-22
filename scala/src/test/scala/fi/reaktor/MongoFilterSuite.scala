package fi.reaktor

import org.scalatra._
import org.scalatra.test.scalatest._
import org.scalatest.matchers._

class MongoFilterSuite extends ScalatraFunSuite with ShouldMatchers {
  addFilter(classOf[MongoFilter], "/*")

  test("GET / returns status 200") {
    get("/") { 
      status should equal (200)
    }
  }
}
