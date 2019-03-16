package usecases.dtos.output

import domain.models.CardId
import play.api.libs.json.{ Format, Json }
import usecases.dtos.OutputDto

// 作成されていればSomeでIDを返し、失敗したらNoneを返す
case class PraiseCardOutputDto(praisedCardId: Option[String]) extends OutputDto

object PraiseCardOutputDto {
  implicit val jsonFormat: Format[PraiseCardOutputDto] = Json.format[PraiseCardOutputDto]
}
