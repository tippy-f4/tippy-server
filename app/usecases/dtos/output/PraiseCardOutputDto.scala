package usecases.dtos.output

import play.api.libs.json.{ Format, Json }
import usecases.dtos.OutputDto

case class PraiseCardOutputDto(praised_card_id: String) extends OutputDto

object PraiseCardOutputDto {
  implicit val jsonFormat: Format[PraiseCardOutputDto] = Json.format[PraiseCardOutputDto]
}
