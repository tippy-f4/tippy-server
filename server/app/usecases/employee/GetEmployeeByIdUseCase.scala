package usecases.employee

import domain.models.{ Employee, EmployeeId }
import domain.repositories.UsesEmployeeRepository
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.EmployeeDto
import usecases.dtos.input.GetEmployeeByIdInputDto
import utils.InvalidParameterException

trait GetEmployeeByIdUseCase extends BaseUseCase[GetEmployeeByIdInputDto, EmployeeDto] with UsesEmployeeRepository {
  override def run(inputDto: GetEmployeeByIdInputDto)(implicit session: DBSession): EmployeeDto = {
    val employeeId = EmployeeId(inputDto.employee_id)
    val employeeOpt: Option[Employee] = employeeRepository.findById(employeeId)

    employeeOpt.map(EmployeeDto.model2Dto).getOrElse(throw InvalidParameterException("No employee id"))
  }
}

trait UsesGetEmployeeByIdUseCase {
  val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
}