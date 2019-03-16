package usecases.dtos.input

import play.api.libs.json.{ Format, Json }
import usecases.dtos.InputDto

case class PraiseCardInputDto(employeeId: String, cardId: String) extends InputDto

object PraiseCardInputDto {
  implicit val jsonFormat: Format[PraiseCardInputDto] = Json.format[PraiseCardInputDto]
}
