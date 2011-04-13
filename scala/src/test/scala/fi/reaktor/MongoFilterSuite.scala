package fi.reaktor

import org.scalatra.test.scalatest._
import org.scalatest.matchers._
import net.liftweb.json.Serialization.read
import net.liftweb.json.{Serialization, NoTypeHints}

class MongoFilterSuite extends ScalatraFunSuite with ShouldMatchers {
  addFilter(classOf[MongoFilter], "/*")

// These tests only test the results of the loaded test data (see mongo-koulutus/ruby/load_data.rb)
  test("GET / returns status 200") {
    get("/") { 
      status should equal (200)
    }
  }
}
