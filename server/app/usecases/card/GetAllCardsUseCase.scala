package usecases.card

import domain.repositories.UsesCardRepository
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.CardDto
import usecases.dtos.input.GetAllCardsInputDto
import usecases.dtos.output.GetAllCardsOutputDto

trait GetAllCardsUseCase extends BaseUseCase[GetAllCardsInputDto, GetAllCardsOutputDto]
  with UsesCardRepository {

  override def run(inputDto: GetAllCardsInputDto)(implicit session: DBSession): GetAllCardsOutputDto = {
    val cards = cardRepository.findAll()
    GetAllCardsOutputDto(cards.map(card => CardDto.model2dto(card)))
  }
}

trait UsesGetAllCardsUseCase {
  val getAllCardsUseCase: GetAllCardsUseCase
}