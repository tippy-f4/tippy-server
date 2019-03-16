package usecases.dtos

import domain.models.Card
import play.api.libs.json.{ Format, Json }

case class CardDto(
  message: String,
  employee_name: String,
  employee_image: String,
  prise_count: Int,
  created_at: String
)

object CardDto {
  implicit val jsonFormat: Format[CardDto] = Json.format[CardDto]

  def model2dto(card: Card): CardDto = {
    val employee = card.targetEmployee
    CardDto(
      card.message.value,
      employee.nickName.value,
      employee.faceImage.url,
      card.employeeIdPraised.length,
      card.createdAt.toString
    )
  }
}