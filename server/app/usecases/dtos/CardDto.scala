package usecases.dtos

import domain.models.Card

case class CardDto(
  message: String,
  employee_name: String,
  employee_image: String,
  created_at: String
)

object CardDto {
  def model2dto(card: Card): CardDto = {
    val employee = card.targetEmployee
    CardDto(
      card.message.value,
      employee.nickName.value,
      employee.faceImage.url,
      card.createdAt.toString
    )
  }
}