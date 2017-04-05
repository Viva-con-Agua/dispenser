package models



case class globalNavEntry(menu: globalentry, submenu: [globalentry])

case class globalNav(entry: [globalNavEntry])
