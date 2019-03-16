package usecases.dtos.input

import play.api.libs.json.{ Format, Json }
import usecases.dtos.InputDto

case class PraiseCardInputDto(card_id: String, employee_id: String) extends InputDto

object PraiseCardInputDto {
  implicit val jsonFormat: Format[PraiseCardInputDto] = Json.format[PraiseCardInputDto]
}
