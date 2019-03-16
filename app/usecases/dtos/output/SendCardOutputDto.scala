package usecases.dtos.output

import domain.models.CardId
import play.api.libs.json.{ Format, Json }
import usecases.dtos.OutputDto

// 作成されていればSomeでIDを返し、失敗したらNoneを返す
case class SendCardOutputDto(created_card_id: String) extends OutputDto

object SendCardOutputDto {
  implicit val jsonFormat: Format[SendCardOutputDto] = Json.format[SendCardOutputDto]
}
