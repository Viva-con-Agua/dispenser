package daos

import java.util.UUID

import models.ObjectIdWrapper

import scala.concurrent.Future

/*
 * Created by dennis on 09.04.17
 */

/** traits ObjectId
 */
trait ObjectIdResolver {
  def getObjectId(id: UUID):Future[Option[ObjectIdWrapper]]
  def getObjectId(name: String):Future[Option[ObjectIdWrapper]]
}
