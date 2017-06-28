package models

import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import java.util.{Date, UUID}
import play.api.libs.functional.syntax._
import models._
/*
 * Models to handle templates. Dispenser Need a Json in Format: 
 *  
 *
 *  @param Template
 *    Template contain metaData and templateData as Json
 *     Template: {
 *      metaData{
 *        microServiceName: "", 
 *        templateName: "" 
 *        searchEngineKeywords: [" "," ",...]
 *        language: ["en", "de", ...]   //Optional
 *        organization: ""              //Optional
 *        },
 *      templateData{
 *        title: ""
 *        ....
 *      }
 *  @param metaData
 *    contain meta Data for handling template (language, names ...)
 *  @param templateData 
 *    contain variables, they can be use for fill mustache-template
 *
 *
 *
 */

trait MetaDataBase {
  val microServiceName: String
  val template: String
  val searchEngineKeywords: Option[List[String]]
  val language: Option[List[String]]
  val organization: Option[String]

}


case class MetaData(
  microServiceName: String,
  template: String,
  searchEngineKeywords: Option[List[String]],
  language: Option[List[String]],
  organization: Option[String]
)extends MetaDataBase

object MetaData {

  def apply(tuple: (String, String, Option[List[String]], Option[List[String]], Option[String])):MetaData =
    MetaData(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5)

  implicit val metaDataWrites: OWrites[MetaData] = (
    (JsPath \ "microServiceName").write[String] and
    (JsPath \ "templates").write[String] and
    (JsPath \ "searchEngineKeywords").writeNullable[List[String]] and
    (JsPath \ "language").writeNullable[List[String]] and
    (JsPath \ "organization").writeNullable[String]
  )(unlift(MetaData.unapply))

  implicit val metaDataReads: Reads[MetaData] = (
    (JsPath \ "microServiceName").read[String] and
    (JsPath \ "template").read[String] and
    (JsPath \ "searchEngineKeywords").readNullable[List[String]] and
    (JsPath \ "language").readNullable[List[String]] and
    (JsPath \ "organization").readNullable[String]
  ).tupled.map(MetaData( _ ))
}


case class TemplateData(
  title: String,
  header: String,
  body: String
)

object TemplateData {

  def apply(tuple: (String,String, String)) : TemplateData =
    TemplateData(tuple._1, tuple._2, tuple._3)

  implicit val templateDataWrites: OWrites[TemplateData] = (
    (JsPath \ "title").write[String] and
    (JsPath \ "header").write[String] and
    (JsPath \ "body").write[String]
  )(unlift(TemplateData.unapply))

  implicit val templateDataReads:  Reads[TemplateData] = (
    (JsPath \ "title").read[String] and
    (JsPath \ "header").read[String] and
    (JsPath \ "body").read[String]
  ).tupled.map(TemplateData( _ ))
}


case class Template(
  metaData: MetaData,
  templateData: TemplateData
  ) {
    def toTemplateString: Map[String, String] = Map(
    "microServiceName" -> this.metaData.microServiceName
    ,"header" ->  this.templateData.title
    ,"body" -> this.templateData.body
    )
}

object Template{
  
  def apply(tuple: (MetaData, TemplateData)) : Template = 
    Template(tuple._1, tuple._2)

  implicit val templateWrites: OWrites[Template] = (
    (JsPath \ "metaData").write[MetaData] and
    (JsPath \ "templateData").write[TemplateData] 
  )(unlift(Template.unapply))
  
  implicit val templateReads: Reads[Template] = (
    (JsPath \ "metaData").read[MetaData] and
    (JsPath \ "templateData").read[TemplateData] 
  ).tupled.map(Template( _ ))

}


