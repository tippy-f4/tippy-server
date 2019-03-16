package usecases.dtos

import domain.models.Card
import play.api.libs.json.{ Format, Json }

case class CardDto(
  id: String,
  message: String,
  employee_name: String,
  employee_image: String,
  praise_count: Int,
  created_at: String
)

object CardDto {
  implicit val jsonFormat: Format[CardDto] = Json.format[CardDto]

  def model2dto(card: Card): CardDto = {
    val employee = card.targetEmployee
    CardDto(
      card.id.value,
      card.message.value,
      employee.nickName.value,
      employee.faceImage.url,
      card.employeeIdPraised.length,
      card.createdAt.toString
    )
  }
}