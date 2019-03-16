package domain.repositories

import domain.models.{ Employee, EmployeeId, NickName }
import scalikejdbc.DBSession

import scala.util.Try

trait EmployeeRepository {

  def findAll()(implicit session: DBSession): Seq[Employee]

  def findById(id: EmployeeId)(implicit session: DBSession): Option[Employee]

  def findByNickName(nickName: NickName)(implicit session: DBSession): Seq[Employee]

  def register(employee: Employee)(implicit session: DBSession): Try[Employee]

  def update(employee: Employee)(implicit session: DBSession): Try[Employee]
}

trait UsesEmployeeRepository {
  val employeeRepository: EmployeeRepository
}