package usecases.dtos.input

import play.api.libs.json.{ Format, Json }
import usecases.dtos.InputDto

case class GetEmployeeByIdInputDto(
  employee_id: String
) extends InputDto

object GetEmployeeByIdInputDto {
  implicit val jsonFormat: Format[GetEmployeeByIdInputDto] = Json.format[GetEmployeeByIdInputDto]
}