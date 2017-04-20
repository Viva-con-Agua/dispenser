package models

import play.api.libs.json.Json
import java.util.{Date, UUID}

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
  val templateName : String
  val searchEngineKeywords: List[String]
  val language: List[String]
  val organization: String
}

case class MetaDataStub(
  microServiceName: String,
  templateName: String,
  organization: String
) extends MetaDataBase {
  val language: Option[List[String]]
  val searchEngineKeywords: Option[List[String]]
}

object MetaDataStub {
  implicit val metaDataStubJsonFormat = Json.format[MetaDataStub]
}

trait TemplateDataBase {
  val title: String
}

case class TemplateDataStub(
  title: String
) extends TemplateDataBase{}



object TemplateDataStub {
  implicit val templateDataJsonFormat = Json.format[TemplateDataStub]
}

trait TemplateBase {
  val metaData: MetaDataStub
  val templateData : TemplateDataStub
}

case class Template(
  metaData: MetaDataStub,
  templateData: TemplateDataStub
  )extends TemplateBase {}

