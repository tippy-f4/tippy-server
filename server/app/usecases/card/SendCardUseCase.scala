package usecases.card

import domain.models.{Card, CardMessage, EmployeeId}
import domain.repositories.{UsesCardRepository, UsesEmployeeRepository}
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.input.SendCardInputDto
import usecases.dtos.output.SendCardOutputDto

trait SendCardUseCase
  extends BaseUseCase[SendCardInputDto, SendCardOutputDto]
    with UsesCardRepository
    with UsesEmployeeRepository {

  override def run(inputDto: SendCardInputDto)(implicit session: DBSession): SendCardOutputDto = {
    employeeRepository.findById(EmployeeId(inputDto.employeeId)).fold(
       SendCardOutputDto(None)
    )(employee => {
      val card = Card.create(CardMessage(inputDto.message), employee)
      SendCardOutputDto(Some(card.id))
    })
  }
}

trait UsesSendCardUseCase {
  val sendCardUseCase: SendCardUseCase
}
