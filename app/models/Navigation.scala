package models
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.Json


trait MenuBase{
  val name: String
  val subs: Set[String]
}



/**
case class rootMenu(name: String, subs: List[String]) extends MenuBase 

case class SubMenu(name: String, root: String, sub: List[String], link: String, version: String) extends MenuBase

case class entryMenu(name: String, link: String)
**/

