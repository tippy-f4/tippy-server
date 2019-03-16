package domain.repositories

import domain.models.{ Employee, EmployeeId, NickName }
import scalikejdbc.DBSession

import scala.util.Try

trait EmployeeRepository extends F4DBSupport[Employee] {

  def findAll()(implicit session: DBSession = autoSession): Seq[Employee]

  def findById(id: EmployeeId)(implicit session: DBSession = autoSession): Option[Employee]

  def findByNickName(nickName: NickName)(implicit session: DBSession = autoSession): Seq[Employee]

  def register(employee: Employee)(implicit session: DBSession): Try[Employee]

  def update(employee: Employee)(implicit session: DBSession): Try[Employee]
}

trait UsesEmployeeRepository {
  val employeeRepository: EmployeeRepository
}