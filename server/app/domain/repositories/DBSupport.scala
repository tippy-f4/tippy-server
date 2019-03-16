package domain.repositories

import scalikejdbc._

trait DBSupport[A] extends SQLSyntaxSupport[A] {
  def dbName: Any

  override val autoSession = ReadOnlyNamedAutoSession(dbName)
}

trait F4DBSupport[A] extends DBSupport[A] {
  override def dbName: Any = NamedDB('f4)

  def conPool(): NamedDB = NamedDB('f4)

  def readOnly[M](f: DBSession => M): M = conPool().readOnly { session => f(session) }

  def transaction[M](f: DBSession => M): M = conPool().localTx { session =>
    f(session)
  }
}