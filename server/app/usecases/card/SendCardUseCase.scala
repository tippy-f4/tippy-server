package usecases.card

import domain.models.{Card, CardMessage, EmployeeId}
import domain.repositories.{UsesCardRepository, UsesEmployeeRepository}
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.input.SendCardInputDto
import usecases.dtos.output.SendCardOutputDto

import scala.util.{Failure, Success, Try}

trait SendCardUseCase
  extends BaseUseCase[SendCardInputDto, SendCardOutputDto]
    with UsesCardRepository
    with UsesEmployeeRepository {

  override def run(inputDto: SendCardInputDto)(implicit session: DBSession): SendCardOutputDto = {
    Try {
      employeeRepository.findById(EmployeeId(inputDto.employeeId)).fold(
        throw new RuntimeException("Employee not found")
      )(employee => {
        val newCard = Card.create(CardMessage(inputDto.message), employee)
        cardRepository.register(newCard).fold(
          throw new RuntimeException("Failed creating card"),
          identity
        )
      })
    } match {
      case Success(card) => SendCardOutputDto(Some(card.id))
      case Failure(_) => SendCardOutputDto(None)
    }
  }
}

trait UsesSendCardUseCase {
  val sendCardUseCase: SendCardUseCase
}
