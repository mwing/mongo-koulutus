package fi.reaktor

import org.scalatra.test.scalatest._
import org.scalatest.matchers._
import net.liftweb.json.Serialization.read
import net.liftweb.json.{Serialization, NoTypeHints}

class MongoFilterSuite extends ScalatraFunSuite with ShouldMatchers {
  addFilter(classOf[MongoFilter], "/*")

  implicit val formats = Serialization.formats(NoTypeHints) + new VenueSerializer

// These tests only test the results of the loaded test data (see mongo-koulutus/ruby/load_data.rb)
  test("GET / returns status 200") {
    get("/") { 
      status should equal (200)
    }
  }

  test("List of venues is not empty") {
    get("/venues/list") {
      read[List[Venue]](response.getContent).size should equal(131)
    }
  }

  test("Searching for 'Zorbas' matches to 'Taverna Zorbas'") {
    get("/venues/search/Zorbas") {
      val results = read[List[Venue]](response.getContent)
      results.size should equal(1)
      results.head.name.toString should equal("Taverna Zorbas")
    }
  }

  test("Searching for locations near 24,60 should yield the expected results") {
    get("/venues/near/24/60") {
      val results = read[List[Venue]](response.getContent)
      results.size should equal(33)
    }
  }
}
