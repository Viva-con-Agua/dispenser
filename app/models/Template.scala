package models


case class MetaData (
  msName: String,
  templateName: String,
  language: Option[List[String]]
)
case class TemplateData (
  title: String,
  header: String,
  body: String
)

case class Template (
  metaData: MetaData,
  templateData: TemplateData
)

object JsonFormatsTemplate {
  import play.api.libs.json.Json

  implicit val metaDataFormat = Json.format[MetaData]
  implicit val templateDataFormat = Json.format[TemplateData]
  implicit val templateFormat = Json.format[Template]
}

