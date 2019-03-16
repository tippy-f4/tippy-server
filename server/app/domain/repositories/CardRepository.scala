package domain.repositories

import domain.models.{ Card, CardId }
import scalikejdbc.DBSession

import scala.util.Try

trait CardRepository extends F4DBSupport[Card] {

  def findAll()(implicit session: DBSession = autoSession): Seq[Card]

  def findById(id: CardId)(implicit session: DBSession = autoSession): Option[Card]

  def register(card: Card)(implicit session: DBSession): Try[Card]

  def update(card: Card)(implicit session: DBSession): Try[Card]
}

trait UsesCardRepository {
  val cardRepository: CardRepository
}
