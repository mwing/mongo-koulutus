package fi.reaktor

import net.liftweb.record.field.StringField
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoId, MongoRecord}
import net.liftweb.mongodb.MongoIdentifier
import com.foursquare.rogue.LatLong
import net.liftweb.mongodb.record.field._
import net.liftweb.mongodb.record.field.MongoCaseClassField
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.{TypeInfo, Formats, Serializer}

class VenueSerializer extends Serializer[Venue] {
  private val venueClass = classOf[Venue]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Venue] = {
    case (TypeInfo(venueClass, _), json) => Venue.fromJValue(json).get
  }
  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: Venue => x.asJValue
  }
}

class UserSerializer extends Serializer[User] {
  private val userClass = classOf[User]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), User] = {
    case (TypeInfo(userClass, _), json) => User.fromJValue(json).get
  }
  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: User => x.asJValue
  }
}

object ThreeSquareMongoIdentifier extends MongoIdentifier {
  override val jndiName = "3sq"
}

class Venue extends MongoRecord[Venue] with MongoId[Venue] {
  def meta = Venue
  object name extends StringField(this, 255)
  object location extends MongoCaseClassField[Venue, LatLong](this)
}

object Venue extends Venue with MongoMetaRecord[Venue] {
  override def mongoIdentifier = ThreeSquareMongoIdentifier
}

class User extends MongoRecord[User] with MongoId[User] {
  def meta = User
  object name extends StringField(this, 255)
  object friends extends MongoListField[User, String](this)
}

object User extends User with MongoMetaRecord[User] {
  override def mongoIdentifier = ThreeSquareMongoIdentifier
}