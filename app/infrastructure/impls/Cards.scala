package infrastructure.impls

import java.time.LocalDateTime

import domain.models._
import scalikejdbc._


case class Cards(id: String, message: String, employeeId: String, createdAt: LocalDateTime) {
  def asModel(employees: Employees, praisesSeq: Seq[Praises]): Card = {
    val employee = employees.asModel()
    new Card(
      CardId(id),
      CardMessage(message),
      employee,
      praisesSeq.map { case Praises(_, eId) => EmployeeId(eId) },
      createdAt
    )
  }
}

object Cards extends SQLSyntaxSupport[Cards] {
  override val tableName = "cards"
  override val columns = Seq("id", "message", "employee_id", "created_at")

  def apply(c: SyntaxProvider[Cards])(rs: WrappedResultSet): Cards = {
    new Cards(
      rs.get(c.resultName.id),
      rs.get(c.resultName.message),
      rs.get(c.resultName.employeeId),
      rs.get(c.resultName.createdAt)
    )
  }

  def apply(rn: ResultName[Cards])(rs: WrappedResultSet): Cards = {
    new Cards(
      rs.get(rn.id),
      rs.get(rn.message),
      rs.get(rn.employeeId),
      rs.get(rn.createdAt)
    )
  }
}