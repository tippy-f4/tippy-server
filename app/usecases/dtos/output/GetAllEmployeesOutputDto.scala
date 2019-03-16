package usecases.dtos.output

import play.api.libs.json.{ Format, Json }
import usecases.dtos.{ EmployeeDto, OutputDto }

case class GetAllEmployeesOutputDto(
  employees: Seq[EmployeeDto]
) extends OutputDto

object GetAllEmployeesOutputDto {
  implicit val jsonFormat: Format[GetAllEmployeesOutputDto] = Json.format[GetAllEmployeesOutputDto]
}
