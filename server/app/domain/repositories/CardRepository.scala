package domain.repositories

import domain.models.{ Card, CardId, EmployeeId }
import scalikejdbc.DBSession

import scala.util.Try

trait CardRepository {

  def findAll()(implicit session: DBSession): Seq[Card]

  def findById(id: CardId)(implicit session: DBSession): Option[Card]

  def register(card: Card)(implicit session: DBSession): Try[Card]

  def update(card: Card, praises: (CardId, EmployeeId))(implicit session: DBSession): Try[Card]
}

trait UsesCardRepository {
  val cardRepository: CardRepository
}
