package infrastructure.impls

import scalikejdbc._

trait DBSupport[A] extends SQLSyntaxSupport[A] {
  def dbName: Any

  override val autoSession = ReadOnlyNamedAutoSession(dbName)
}

trait F4DBSupport[A] extends DBSupport[A] {
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://localhost:3306/f4?useSSL=false", "calcio", "")

  override def dbName: Any = NamedDB('f4)

  def readOnly[M](f: DBSession => M): M = DB.readOnly { session => f(session) }

  def transaction[M](f: DBSession => M): M = DB.localTx { session =>
    f(session)
  }
}

object F4DBSupport extends F4DBSupport[Any]