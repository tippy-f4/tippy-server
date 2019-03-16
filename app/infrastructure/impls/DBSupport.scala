package infrastructure.impls

import scalikejdbc._
import scala.util.Try
import scala.util.Success

trait DBSupport[A] extends SQLSyntaxSupport[A] {
  def dbName: Any

  override val autoSession = ReadOnlyNamedAutoSession(dbName)
}

trait F4DBSupport[A] extends DBSupport[A] {
  val conUrl =
    Try {
      sys.env("DATABASE_URL")
    } match {
      case Success(dbUrl) => {
        val uri = new java.net.URI(dbUrl)
        val userName = uri.getUserInfo().split(":")(0)
        val password = uri.getUserInfo().split(":")(1)
        s"jdbc:postgresql://${uri.getHost}:${uri.getPort}${uri.getPath}?user=${userName}&password=${password}"
      }
      case _ => "jdbc:postgresql://localhost:5432/f4?user=calcio"
    }

  ConnectionPool.singleton(conUrl, "", "")

  override def dbName: Any = NamedDB('f4)

  def readOnly[M](f: DBSession => M): M = DB.readOnly { session => f(session) }

  def transaction[M](f: DBSession => M): M = DB.localTx { session =>
    f(session)
  }
}

object F4DBSupport extends F4DBSupport[Any]