package infrastructure.impls

import java.time.LocalDateTime

import domain.models._
import scalikejdbc._


case class Cards(id: String, message: String, employeeId: String, createdAt: LocalDateTime) {

  def asModel(employees: Employees): Card = {
    val employee = employees.asModel()
    new Card(
      CardId(id),
      CardMessage(message),
      employee,
      createdAt
    )
  }
}

object Cards extends SQLSyntaxSupport[Cards] {
  override val tableName = "cards"
  override val columns = Seq("id", "message", "employee_id", "created_at")

  def apply(c: SyntaxProvider[Cards])(rs: WrappedResultSet): Cards = {
    Cards(
      rs.get(c.resultName.id),
      rs.get(c.resultName.message),
      rs.get(c.resultName.employeeId),
      rs.get(c.resultName.createdAt)
    )
  }
}