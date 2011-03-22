package fi.reaktor

import org.scalatra._
import net.liftweb.json.JsonAST
import net.liftweb.json.JsonDSL
import net.liftweb.json.JsonParser
import java.net.URL

class MongoFilter extends ScalatraFilter {

  get("/") {
    <html><head><title>mongo-koulutus</title></head><body><h4>Not implemented</h4></body></html>
  }

 notFound {
    response.setStatus(404)
    <html>
      <body>
        <h1>404 Not Found</h1>
      </body>
    </html>
  }
}
