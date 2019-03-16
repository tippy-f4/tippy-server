package domain.models

import java.time.LocalDateTime
import java.util.UUID

class Card (
  val id: CardId,
  val message: CardMessage,
  val targetEmployee: Employee,
  val createdAt: LocalDateTime
)

case class CardId(value: String) extends AnyVal
case class CardMessage(value: String) extends AnyVal

object Card {
  def create(message: CardMessage, employee: Employee): Card =
    new Card(
      CardId(UUID.randomUUID().toString),
      message,
      employee,
      LocalDateTime.now()
    )
}
