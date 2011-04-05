package fi.reaktor

import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.record.field.StringField
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoId, MongoRecord}
import net.liftweb.mongodb.MongoIdentifier
import com.foursquare.rogue.LatLong
import net.liftweb.mongodb.record.field.MongoCaseClassField

class VenueSerializer extends Serializer[Venue] {
  private val venueClass = classOf[Venue]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Venue] = {
    case (TypeInfo(venueClass, _), json) => json match {
      case JObject( JField("_id", JString(id)) ::
                    JField("name", JString(name)) ::
                    JField("location", JArray(List(JDouble(lat), JDouble(long)))) ::
                    Nil) => Venue
      case x => throw new MappingException("Can't convert " + x + " to Venue")
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: Venue => x.asJValue
  }
}

class Venue extends MongoRecord[Venue] with MongoId[Venue] {
  def meta = Venue
  object name extends StringField(this, 255)
  object location extends MongoCaseClassField[Venue, LatLong](this)
}

object ThreeSquareMongoIdentifier extends MongoIdentifier {
  override val jndiName = "3sq"
}

object Venue extends Venue with MongoMetaRecord[Venue] {
  override def mongoIdentifier = ThreeSquareMongoIdentifier
}
