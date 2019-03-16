package usecases.card

import domain.models.{ Card, CardMessage, EmployeeId }
import domain.repositories.{ UsesCardRepository, UsesEmployeeRepository }
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.input.SendCardInputDto
import usecases.dtos.output.SendCardOutputDto
import utils.InvalidParameterException

trait SendCardUseCase
  extends BaseUseCase[SendCardInputDto, SendCardOutputDto]
    with UsesCardRepository
    with UsesEmployeeRepository {

  override def run(inputDto: SendCardInputDto)(implicit session: DBSession): SendCardOutputDto = {
    val outputDtoOpt =
      for {
        employee <- employeeRepository.findById(EmployeeId(inputDto.employee_id))
        newCard = Card.create(CardMessage(inputDto.message), employee)
        card <- cardRepository.register(newCard).toOption
      } yield {
        SendCardOutputDto(card.id.value)
      }

    outputDtoOpt.getOrElse(throw InvalidParameterException("Error on saving new card"))
  }
}

trait UsesSendCardUseCase {
  val sendCardUseCase: SendCardUseCase
}
