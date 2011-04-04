package fi.reaktor

import net.liftweb.json._
import net.liftweb.json.JsonAST._


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
