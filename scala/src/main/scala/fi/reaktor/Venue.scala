package fi.reaktor

import net.liftweb.record.field._
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoId, MongoRecord}
import net.liftweb.mongodb.MongoIdentifier
import com.foursquare.rogue.LatLong
import net.liftweb.mongodb.record.field._

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
