package infrastructure.impls

import domain.models.{ Card, CardId, EmployeeId }
import domain.repositories.CardRepository
import scalikejdbc._

import scala.util.Try

class CardRepositoryImpl extends CardRepository with F4DBSupport[Card]{
  val (c, e, p) = (Cards.syntax("c"), Employees.syntax("e"), Praises.syntax("p"))

  override def findAll()(implicit session: DBSession): Seq[Card] = {
    val cardsAndEmployees: Seq[(Cards, Employees)] =
      withSQL {
        select.from(Cards as c).innerJoin(Employees as e).on(c.employeeId, e.id)
      }.map { rs =>
        (Cards(c)(rs), Employees(e)(rs))
      }.list().apply()

    // TODO N+1
    cardsAndEmployees.map { case (cards, employees) =>
      val praisesSeq =
        withSQL {
          select.from(Praises as p).innerJoin(Cards as c).on(p.cardId, c.id).where.eq(p.cardId, cards.id)
        }.map { rs => Praises(p)(rs) }.list().apply()
      cards.asModel(employees, praisesSeq)
    }
  }

  override def findById(cardId: CardId)(implicit session: DBSession): Option[Card] = {
    withSQL {
      select.from(Cards as c).innerJoin(Employees as e).on(c.employeeId, e.id).where.eq(c.id, cardId.value)
    }.map { rs =>
      (Cards(c)(rs), Employees(e)(rs))
    }.single().apply()
      .map { case (cards, employees) =>
        val praisesSeq =
          withSQL {
            select.from(Praises as p).innerJoin(Cards as c).on(p.cardId, c.id).where.eq(p.cardId, cards.id)
          }.map { rs => Praises(p)(rs) }.list().apply()
        cards.asModel(employees, praisesSeq)
      }
  }

  override def register(card: Card)(implicit session: DBSession): Try[Card] = {
    Try {
      withSQL {
        insert.into(Cards).namedValues(
          Cards.column.id -> card.id.value,
          Cards.column.message -> card.message.value,
          Cards.column.employeeId -> card.targetEmployee.id.value,
          Cards.column.createdAt -> card.createdAt
        )
      }.update().apply()
      card
    }
  }

  override def update(card: Card, praises: (CardId, EmployeeId))(implicit session: DBSession): Try[Card] = {
    Try {
      withSQL {
        QueryDSL.update(Cards).set(
          Cards.column.id -> card.id.value,
          Cards.column.message -> card.message.value,
          Cards.column.employeeId -> card.targetEmployee.id.value,
          Cards.column.createdAt -> card.createdAt
        )
      }.update().apply()

      withSQL {
        QueryDSL.update(Praises).set(
          Praises.column.cardId -> praises._1.value,
          Praises.column.employeeId -> praises._2.value,
        )
      }.update().apply()
      card
    }
  }
}

trait MixInCardRepository {
  val cardRepository = new CardRepositoryImpl
}