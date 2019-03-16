package infrastructure.impls

import domain.models.{ Card, CardId }
import domain.repositories.CardRepository
import scalikejdbc._

import scala.util.Try

class CardRepositoryImpl extends CardRepository with F4DBSupport[Card]{
  val (c, e) = (Cards.syntax("p"), Employees.syntax("e"))

  override def findAll()(implicit session: DBSession): Seq[Card] = {
    withSQL {
      select.from(Cards as c).innerJoin(Employees as e).on(c.employeeId, e.id)
    }.map { rs =>
      (Cards(c)(rs), Employees(e)(rs))
    }.list.apply()
      .map { case (cards, employees) => cards.asModel(employees) }
  }

  override def findById(cardId: CardId)(implicit session: DBSession): Option[Card] = {
    withSQL {
      select.from(Cards as c).innerJoin(Employees as e).on(c.employeeId, e.id).where.eq(c.id, cardId.value)
    }.map { rs =>
      (Cards(c)(rs), Employees(e)(rs))
    }.single().apply()
      .map { case (cards, employees) => cards.asModel(employees) }
  }

  override def register(card: Card)(implicit session: DBSession): Try[Card] = {
    Try {
      withSQL {
        insert.into(Cards).namedValues(
          c.id -> card.id.value,
          c.message -> card.message.value,
          c.employeeId -> card.targetEmployee.id.value,
          c.createdAt -> card.createdAt
        )
      }.update().apply()
      card
    }
  }

  override def update(card: Card)(implicit session: DBSession): Try[Card] = {
    Try {
      withSQL {
         QueryDSL.update(Cards).set(
          c.id -> card.id.value,
          c.message -> card.message.value,
          c.employeeId -> card.targetEmployee.id.value,
          c.createdAt -> card.createdAt
        )
      }.update().apply()
      card
    }
  }
}

trait MixInCardRepository {
  val cardRepository = new CardRepositoryImpl
}
