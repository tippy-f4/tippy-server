package infrastructure.impls

import scalikejdbc._

case class Praises(cardId: String, employeeId: String)

object Praises extends SQLSyntaxSupport[Praises] {
  override val tableName = "praises"
  override val columns = Seq("card_id", "employee_id")

  def apply(p: SyntaxProvider[Praises])(rs: WrappedResultSet): Praises = {
    new Praises(
      rs.get(p.resultName.cardId),
      rs.get(p.resultName.employeeId)
    )
  }

  def apply(rn: ResultName[Praises])(rs: WrappedResultSet): Praises = {
    new Praises(
      rs.get(rn.cardId),
      rs.get(rn.employeeId),
    )
  }
}
