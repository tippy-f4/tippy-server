package usecases.dtos.output

import play.api.libs.json.{ Format, Json }
import usecases.dtos.OutputDto

case class GetCardsByEmployeeIdOutputDto(
  cards: Seq[CardWithEnablePraiseFlagDto]
) extends OutputDto

object GetCardsByEmployeeIdOutputDto {
  implicit val jsonFormat: Format[GetCardsByEmployeeIdOutputDto] = Json.format[GetCardsByEmployeeIdOutputDto]

}
