package fi.reaktor

import org.scalatra._
import net.liftweb.json._
import net.liftweb.json.Serialization.{read, write}
import com.novus.casbah.mongodb.{MongoDBObject, MongoConnection}

class MongoFilter extends ScalatraFilter {

  implicit val formats = Serialization.formats(NoTypeHints) + new VenueSerializer
  val venues = MongoConnection()("3sq")("venues")

  get("/") {
    <html>
      <head>
        <title>mongo-koulutus</title>
      </head> <body>
      <h4>Not implemented</h4>
    </body>
    </html>
  }

  get("/venues/list") {
    val all = for (x <- venues.find()) yield read[Venue](x.toString)
    write(all.toList)
  }

  get("/venues/search/:venue") {
    val query = MongoDBObject("name" -> params("venue").r)
    val results = for (x <- venues.find(query)) yield read[Venue](x.toString)
    write(results.toList)
  }

  get("/venues/near/:lat/:long") {
    val location = List(params("lat").toDouble, params("long").toDouble)
    val q = MongoDBObject("location" -> MongoDBObject("$near" -> location, "$maxDistance" -> 0.1))  // maxDistance: 1 ~ 70km
    val results = for (x <- venues.find(q)) yield read[Venue](x.toString)
    write(results.toList)
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