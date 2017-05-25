package models

case class TemplateBase(
  title: String,
  body: String
)

object TemplateBase {

  def apply(tuple: (String, String)) : TemplateBase =
    TemplateBase(tuple._1, C:q
    tuple._2)

  implicit val templateBaseWrites: OWrites[TemplateBase] = (
    (JsPath \ "title").write[String] and
    (JsPath \ "body").write[String]
  )(unlift(TemplateBase.unapply))

  implicit val templateBaseReads:  Reads[TemplateBase] = (
    (JsPath \ "title").read[String] and
    (JsPath \ "body").read[String]
  ).tupled.map(TemplateBase( _ ))
}
