package domain.models

import java.time.LocalDateTime
import java.util.UUID

class Card (
  val id: CardId,
  val message: CardMessage,
  val targetEmployee: Employee,
  val employeeIdPraised: Seq[EmployeeId], // ほんとはSeq[Employee]な気がするがDBにrelテーブルができて実装が大変なのでカウントで許してくれ！
  val createdAt: LocalDateTime
)

case class CardId(value: String) extends AnyVal
case class CardMessage(value: String) extends AnyVal
case class PraiseCount(value: Int) extends AnyVal

object Card {
  def create(message: CardMessage, employee: Employee): Card =
    new Card(
      CardId(UUID.randomUUID().toString),
      message,
      employee,
      Nil,
      LocalDateTime.now()
    )
}
