package infrastructure.impls

import domain.models._
import scalikejdbc._

case class Employees(id: String, name: String, nickName: String, faceImage: String) {
  def asModel(): Employee = {
    new Employee(
      EmployeeId(id),
      EmployeeName(name),
      NickName(nickName),
      FaceImage(faceImage)
    )
  }
}

object Employees extends SQLSyntaxSupport[Employees] {
  override val tableName = "employees"
  override val columns = Seq("id", "name", "nick_name", "face_image")

  def apply(e: SyntaxProvider[Employees])(rs: WrappedResultSet): Employees = {
    Employees(
      rs.get(e.resultName.id),
      rs.get(e.resultName.name),
      rs.get(e.resultName.nickName),
      rs.get(e.resultName.faceImage)
    )
  }
}
