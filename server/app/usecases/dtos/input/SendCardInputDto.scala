package usecases.dtos.input

import play.api.libs.json.{Format, Json}
import usecases.dtos.InputDto

case class SendCardInputDto(employeeId: String, message: String) extends InputDto

object SendCardInputDto {
  implicit val jsonFormat: Format[SendCardInputDto] = Json.format[SendCardInputDto]
}
