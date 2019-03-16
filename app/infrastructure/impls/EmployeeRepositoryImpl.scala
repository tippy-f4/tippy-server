package infrastructure.impls

import domain.models.{ Employee, EmployeeId, NickName }
import domain.repositories.EmployeeRepository
import scalikejdbc._

import scala.util.Try

class EmployeeRepositoryImpl extends EmployeeRepository with F4DBSupport[Employee] {
  val e = Employees.syntax("e")

  override def findAll()(implicit session: DBSession): Seq[Employee] = {
    withSQL {
      select.from(Employees as e)
    }.map(rs => Employees(e)(rs)).list.apply().map(_.asModel())
  }

  override def findById(employeeId: EmployeeId)(implicit session: DBSession): Option[Employee] = {
    withSQL {
      select.from(Employees as e).where.eq(e.id, employeeId.value)
    }.map(rs => Employees(e)(rs)).single.apply().map(_.asModel())
  }

  override def findByNickName(nickName: NickName)(implicit session: DBSession): Seq[Employee] = {
    withSQL {
      select.from(Employees as e).where.eq(e.id, nickName.value)
    }.map(rs => Employees(e)(rs)).list.apply().map(_.asModel())
  }

//  override def register(employee: Employee)(implicit session: DBSession): Try[Employee] = ???
//
//  override def update(employee: Employee)(implicit session: DBSession): Try[Employee] = ???
}

trait MixInEmployeeRepository {
  val employeeRepository = new EmployeeRepositoryImpl
}
