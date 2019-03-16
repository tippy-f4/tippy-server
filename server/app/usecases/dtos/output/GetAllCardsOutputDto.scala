package usecases.dtos.output

import usecases.dtos.{ CardDto, OutputDto }
import play.api.libs.json.{ Format, Json }

case class GetAllCardsOutputDto(
  cards: Seq[CardDto]
) extends OutputDto

object GetAllCardsOutputDto {
  implicit val jsonFormat: Format[GetAllCardsOutputDto] = Json.format[GetAllCardsOutputDto]
}