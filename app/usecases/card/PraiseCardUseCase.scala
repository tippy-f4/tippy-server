package usecases.card

import domain.models.EmployeeId
import domain.repositories.{UsesCardRepository, UsesEmployeeRepository}
import scalikejdbc.DBSession
import usecases.BaseUseCase
import usecases.dtos.input.PraiseCardInputDto
import usecases.dtos.output.PraiseCardOutputDto

trait PraiseCardUseCase
  extends BaseUseCase[PraiseCardInputDto, PraiseCardOutputDto]
    with UsesCardRepository
    with UsesEmployeeRepository {

  override def run(inputDto: PraiseCardInputDto)(implicit session: DBSession): PraiseCardOutputDto = {
    employeeRepository.findById(EmployeeId(inputDto.employeeId)).fold(
       PraiseCardOutputDto(None)
    )(employee => {
      // TODO: あとでつくる
      PraiseCardOutputDto(None)
    })
  }
}

trait UsesPraiseUseCase {
  val praiseCardUseCase: PraiseCardUseCase
}
