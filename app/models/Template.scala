package models

import java.util.UUID

case class MetaData (
  msName: String,
  templateName: String,
  language: Option[List[String]]
)
case class TemplateData (
  title: String,
  content: String,
  head: Option[String]
)

case class NavigationData (
  navigationName: String,
  active: String,
  user_id: Option[UUID]
)

case class Template (
  metaData: MetaData,
  navigationData: NavigationData,
  templateData: TemplateData
)

object JsonFormatsTemplate {
  import play.api.libs.json.Json

  implicit val metaDataFormat = Json.format[MetaData]
  implicit val templateDataFormat = Json.format[TemplateData]
  implicit val navigationDataFormat = Json.format[NavigationData]
  implicit val templateFormat = Json.format[Template]
}

