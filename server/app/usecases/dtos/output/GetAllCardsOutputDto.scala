package usecases.dtos.output

import usecases.dtos.CardDto

case class GetAllCardsOutputDto(
  cards: Seq[CardDto]
)
