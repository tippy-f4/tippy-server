package usecases.card

import domain.models.{ CardId, EmployeeId }
import domain.repositories.{ UsesCardRepository, UsesEmployeeRepository }
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.input.PraiseCardInputDto
import usecases.dtos.output.PraiseCardOutputDto
import utils.InvalidParameterException

trait PraiseCardUseCase
  extends BaseUseCase[PraiseCardInputDto, PraiseCardOutputDto]
    with UsesCardRepository
    with UsesEmployeeRepository {

  override def run(inputDto: PraiseCardInputDto)(implicit session: DBSession): PraiseCardOutputDto = {
    val praiseCardOutputDtoOpt =
      for {
        employee <- employeeRepository.findById(EmployeeId(inputDto.employee_id))
        card <- cardRepository.findById(CardId(inputDto.card_id))
        updatedCard <- cardRepository.update(card, (card.id, employee.id)).toOption
        if (card.employeeIdPraised != employee.id) && (!card.employeeIdPraised.contains(employee.id))
      } yield {
        PraiseCardOutputDto(updatedCard.id.value)
      }
    praiseCardOutputDtoOpt.getOrElse(throw InvalidParameterException("Error on praising the card"))
  }
}

trait UsesPraiseUseCase {
  val praiseCardUseCase: PraiseCardUseCase
}
