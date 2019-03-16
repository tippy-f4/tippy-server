package domain.models

class Praise private(
  val id: PraiseId,
  val targetEmployee: Employee,
  val targetCard: Card
)

case class PraiseId(value: String) extends AnyVal

object Praise {

}
