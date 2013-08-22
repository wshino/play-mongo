package models

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import org.joda.time.DateTime
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class MyModelSpec extends Specification {

  "MyModel" should {

    // jodatime のシリアライズの場合は必ず書くこと
    RegisterJodaTimeConversionHelpers()
    RegisterConversionHelpers()

    "try" in {
      running(FakeApplication()) {

        // mymodel の中身を空にする
        MyModel.remove(MongoDBObject.empty)

        val now = new DateTime
        val m = MyModel.apply(id = new ObjectId, name = "hoge", date = now)
        println(s"m = $m")

        val dbo = MyModel.toDBObject(m)
        println(s"dbo = $dbo")
        val cj = MyModel.toCompactJson(m)
        println(s"cj = $cj")

        val res = MyModel.insert(m)
        println(s"res = $res")

        MyModel.count(MongoDBObject("name" -> "hoge")) mustEqual 1

        MyModel.findByDate(now).head.name mustEqual "hoge"

        MyModel.findOneByName("hoge").map(r => r.name mustEqual "hoge")

        MyModel.findOneByName("fuga") match {
          case Some(x) => false mustEqual true
          case _ => true mustEqual true
        }

      }
    }

  }
}