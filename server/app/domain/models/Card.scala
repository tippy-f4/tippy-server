package domain.models

import org.joda.time.DateTime

class Card(
  val id: CardId,
  val employee: Employee,
  val message: CardMessage,
  val createdAt: DateTime
)

case class CardId(value: String) extends AnyVal
case class CardMessage(value: String) extends AnyVal