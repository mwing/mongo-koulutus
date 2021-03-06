package fi.reaktor

import org.scalatra._
import net.liftweb.json._
import net.liftweb.mongodb._
import com.foursquare.rogue.Rogue._
import com.foursquare.rogue.Degrees
import java.util.regex.Pattern
import com.mongodb.DBAddress
import net.liftweb.mongodb.record.MongoRecord 

class MongoFilter extends ScalatraFilter {

  // Use this for single instance mongo
  MongoDB.defineDb(ThreeSquareMongoIdentifier, MongoAddress(MongoHost("127.0.0.1", 27017), "3sq"))

  //use this for replicaset, works for single instance as well, but throws exceptions due to not reaching the 2nd server
  //val replicaSet = MongoPair(new DBAddress("127.0.0.1", 27017, "3sq"), new DBAddress("127.0.0.1", 37017, "3sq"))
  //MongoDB.defineDb(ThreeSquareMongoIdentifier, MongoAddress(replicaSet, "3sq"))

  get("/users/list") {
   renderObjects(User.fetch)
  }

  get("/friends/:user/list") {
    val user = User.where(_.name eqs params("user")).get
    user.get.friends.get.mkString("{friends:[", ",", "]}") // Well only return friends here, so no asJSON method available
  }

  get("/friends/:user/add/:friend") {
    val query = User.where(_.name eqs params("user")) modify (_.friends push  params("friend")) updateOne()
    User.where(_.name eqs params("user")).get.get.asJSON
  }

  get("/friends/:user/remove/:friend") {
    val query = User.where(_.name eqs params("user")) modify (_.friends pull params("friend")) updateOne()
    User.where(_.name eqs params("user")).get.get.asJSON
  }

  get("/venues/list") {
    renderObjects(Venue.fetch)
  }

  get("/venues/search/:name") {
    val all = Venue where(_.name regexWarningNotIndexed Pattern.compile(params("name")))
    renderObjects(all.fetch)
  }

  get("/venues/near/:lat/:long") {
    val lat = params("lat").toLong
    val long = params("long").toLong
    val distance = Degrees(1)
    val q = Venue where(_.location near (lat, long, distance))
    renderObjects(q.fetch)
  }

  get("/") {
    <html>
      <head>
        <title>MongoDB</title>
      </head> <body>
      <h4>Urls</h4>
      <ul>
         <li><a href="/users/list">/users/list</a> - List all users</li>
         <li><a href="/venues/list">/venues/list</a> - List all venues</li>
         <li><a href="/venues/search/Zorbas">/venues/search/(part of venue name)</a> - Search for venue matching the term</li>
         <li><a href="/venues/near/24/60">/venues/near/(latitude)/(longtitude)</a> - Search for venues near the provided coordinates</li>
      </ul>
    </body>
    </html>
  }

 def renderObjects(objs: List[MongoRecord[_]]) = compact(render(JArray(objs.map(_.asJValue))))
}
