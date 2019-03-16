package usecases.employee

import domain.models.Employee
import domain.repositories.UsesEmployeeRepository
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.EmployeeDto
import usecases.dtos.input.GetAllEmployeesInputDto
import usecases.dtos.output.GetAllEmployeesOutputDto

trait GetAllEmployeesUseCase extends BaseUseCase[GetAllEmployeesInputDto, GetAllEmployeesOutputDto] with UsesEmployeeRepository {
  override def run(inputDto: GetAllEmployeesInputDto)(implicit session: DBSession): GetAllEmployeesOutputDto = {
    val employees: Seq[Employee] = employeeRepository.findAll()
    GetAllEmployeesOutputDto(employees.map(EmployeeDto.model2Dto))
  }
}

trait UsesGetAllEmployeesUseCase {
  val getAllEmployeesUseCase: GetAllEmployeesUseCase
}
