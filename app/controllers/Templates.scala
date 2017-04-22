package controllers


import play.api._
import play.api.cache._
import play.api.mvc._
import play.api.mvc.Result
import javax.inject.{Inject, Singleton}
import com.github.tototoshi.play2.scalate._
import models._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.concurrent.ExecutionContext.Implicits.global


class Templates @Inject() (
  scalate: Scalate
  ,cache: CacheApi
) extends Controller{


  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
  
  def testDummy = Action(parse.json) { request =>
    
    val template: JsResult[Template] = request.body.validate[Template] 
    template match {
      case s: JsSuccess[Template] =>
        val metaData: MetaData = MetaData(
          s.get.metaData.microServiceName, 
          s.get.metaData.template,
          s.get.metaData.searchEngineKeywords,
          s.get.metaData.language,
          s.get.metaData.organization
          )
        Ok(scalate.render("testDummy.mustache", Map("name" -> metaData.microServiceName)))
      case e: JsError => BadRequest("Detect error:" + JsError.toJson(e))
    }
    
  }
}
