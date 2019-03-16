package usecases.dtos.output

import domain.models.Card
import play.api.libs.json.{ Format, Json }

case class CardWithEnablePraiseFlagDto(
  id: String,
  message: String,
  employee_name: String,
  employee_image: String,
  praise_count: Int,
  enable_praise: Boolean,
  created_at: String
)

object CardWithEnablePraiseFlagDto {
  implicit val jsonFormat: Format[CardWithEnablePraiseFlagDto] = Json.format[CardWithEnablePraiseFlagDto]

  def apply(card: Card, enablePraiseFlag: Boolean): CardWithEnablePraiseFlagDto = {
    new CardWithEnablePraiseFlagDto(
      card.id.value,
      card.message.value,
      card.targetEmployee.nickName.value,
      card.targetEmployee.faceImage.url,
      card.employeeIdPraised.length,
      enablePraiseFlag,
      card.createdAt.toString
    )
  }
}
