package models

import com.novus.salat.global._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import org.joda.time.DateTime


object MyModel extends ModelCompanion[MyModel, ObjectId] {
  val collection = MongoConnection()("my_db")("my_model_coll")
  val dao = new SalatDAO[MyModel, ObjectId](collection = collection) {}

  def findOneByName(name: String): Option[MyModel] = dao.findOne(MongoDBObject("name" -> name))

  def findByDate(date: DateTime) = dao.find(MongoDBObject("date" -> date)).toList
}

case class MyModel(@Key("_id") id: ObjectId,
                   name: String,
                   date: DateTime)