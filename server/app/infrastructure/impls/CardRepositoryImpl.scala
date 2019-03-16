package infrastructure.impls

import domain.models.{ Card, CardId }
import domain.repositories.{ CardRepository, F4DBSupport }
import scalikejdbc.DBSession

import scala.util.Try

class CardRepositoryImpl extends CardRepository {
  override def findAll()(implicit session: DBSession): Seq[Card] = {

  }

  override def findById(id: CardId)(implicit session: DBSession): Option[Card] = ???

  override def register(card: Card)(implicit session: DBSession): Try[Card] = ???

  override def update(card: Card)(implicit session: DBSession): Try[Card] = ???
}
