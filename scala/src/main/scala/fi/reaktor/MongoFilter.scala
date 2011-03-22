package fi.reaktor

import org.scalatra._
import net.liftweb.json.JsonAST
import net.liftweb.json.JsonDSL
import net.liftweb.json.JsonParser
import java.net.URL

class MongoFilter extends ScalatraFilter {

  get("/") {
    "{}"
  }
}
