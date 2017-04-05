package models



case class linkdata(label: String, link: String)
case class template(name: String, menu: String)

case class globalEntry(hierarchy: String, link: linkdata, accesscontrol: [])

