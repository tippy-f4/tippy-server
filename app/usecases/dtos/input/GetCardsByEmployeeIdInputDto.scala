package usecases.dtos.input

import play.api.libs.json.{ Format, Json }
import usecases.dtos.InputDto

case class GetCardsByEmployeeIdInputDto(
  employee_id: String
) extends InputDto

object GetCardsByEmployeeIdInputDto {
  implicit val jsonFormat: Format[GetCardsByEmployeeIdInputDto] = Json.format[GetCardsByEmployeeIdInputDto]

}