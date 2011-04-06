package fi.reaktor

import org.scalatra._
import net.liftweb.json._
import net.liftweb.json.Serialization.write
import net.liftweb.mongodb._
import com.foursquare.rogue.Rogue._
import com.foursquare.rogue.Degrees
import java.util.regex.Pattern
import com.mongodb.DBAddress

class MongoFilter extends ScalatraFilter {
  implicit val formats = Serialization.formats(NoTypeHints) + new VenueSerializer

  // Use this for single instance mongo
  MongoDB.defineDb(ThreeSquareMongoIdentifier, MongoAddress(MongoHost("127.0.0.1", 27017), "3sq"))

  //use this for replicaset, works for single instance as well, but throws exceptions due to not reaching the 2nd server
  //val replicaSet = MongoPair(new DBAddress("127.0.0.1", 27017, "3sq"), new DBAddress("127.0.0.1", 37017, "3sq"))
  //MongoDB.defineDb(ThreeSquareMongoIdentifier, MongoAddress(replicaSet, "3sq"))

  get("/venues/list") {
    write(Venue.fetch())
  }

  get("/venues/search/:name") {
    val all = Venue where(_.name regexWarningNotIndexed Pattern.compile(params("name")))
    write(all.fetch)
  }

  get("/venues/near/:lat/:long") {
    val lat = params("lat").toLong
    val long = params("long").toLong
    val distance = Degrees(1)
    val q = Venue where(_.location near (lat, long, distance))
    write(q.fetch)
  }

  get("/") {
    <html>
      <head>
        <title>MongoDB</title>
      </head> <body>
      <h4>Urls</h4>
      <ul>
         <li><a href="/venues/list">/venues/list</a></li>
         <li><a href="/venues/search/Zorbas">/venues/search/(part of venue name)</a></li>
         <li><a href="/venues/near/24/60">/venues/near/(latitude)/(longtitude)</a></li>
      </ul>
    </body>
    </html>
  }
}