package usecases.card

import domain.models.{ Employee, EmployeeId }
import domain.repositories.{ UsesCardRepository, UsesEmployeeRepository }
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.input.GetCardsByEmployeeIdInputDto
import usecases.dtos.output.{ CardWithEnablePraiseFlagDto, GetCardsByEmployeeIdOutputDto }
import utils.InvalidParameterException

trait GetCardsByEmployeeIdUseCase extends BaseUseCase[GetCardsByEmployeeIdInputDto, GetCardsByEmployeeIdOutputDto]
  with UsesCardRepository
  with UsesEmployeeRepository {

  override def run(inputDto: GetCardsByEmployeeIdInputDto)(implicit session: DBSession): GetCardsByEmployeeIdOutputDto = {
    val employeeId = EmployeeId(inputDto.employee_id)
    val employeeOpt: Option[Employee] = employeeRepository.findById(employeeId)
    val a =
    employeeOpt.map { employee =>
      val cards = cardRepository.findAll()
      cards.map { card =>
        val enablePraiseFlag =
          if ((card.targetEmployee.id == employee.id) || card.employeeIdPraised.contains(employee.id)) {
            false
          } else {
            true
          }
        CardWithEnablePraiseFlagDto(card, enablePraiseFlag)
      }
    }
    GetCardsByEmployeeIdOutputDto(a.getOrElse(throw InvalidParameterException("Error on cards for employees")))
  }

}

trait UsesGetCardsByEmployeeIdUseCase {
  val getCardsByEmployeeIdUseCase: GetCardsByEmployeeIdUseCase
}