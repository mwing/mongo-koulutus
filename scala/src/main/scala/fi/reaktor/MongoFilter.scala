package fi.reaktor

import org.scalatra._
import net.liftweb.json._
import net.liftweb.json.JsonAST._
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
    val q = MongoDBObject("location" -> MongoDBObject("$near" -> location, "$maxDistance" -> 0.01))  // maxDistance: 1 ~ 70km
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
case class Location(lat: Double, long: Double)
case class Venue(id: String, name: String, location: Location)

class VenueSerializer extends Serializer[Venue] {
  private val venueClass = classOf[Venue]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Venue] = {
    case (TypeInfo(venueClass, _), json) => json match {
      case JObject( JField("_id", JObject(JField("$oid", JString(id)) :: Nil)) ::
                    JField("name", JString(name)) ::
                    JField("location", JArray(List(JDouble(lat), JDouble(long)))) ::
                    Nil) =>
        new Venue(id, name, Location(lat, long))
      case x => throw new MappingException("Can't convert " + x + " to Venue")
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: Venue =>
      JObject(JField("_id", JObject(JField("$oid", JString(x.id)) :: Nil)) ::
        JField("name", JString(x.name)) ::
        JField("location", JArray(List(JDouble(x.location.lat), JDouble(x.location.long)))) :: Nil)
  }
}
