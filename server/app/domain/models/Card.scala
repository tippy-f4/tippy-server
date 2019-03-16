package domain.models

import java.time.LocalDateTime

class Card(
  val id: CardId,
  val message: CardMessage,
  val targetEmployee: Employee,
  val createdAt: LocalDateTime
)

case class CardId(value: String) extends AnyVal
case class CardMessage(value: String) extends AnyVal