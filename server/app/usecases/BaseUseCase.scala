package usecases

import scalikejdbc.DBSession

trait BaseUseCase[I <: InputDto, O <: OutputDto] {
  def run(inputDto: I)(implicit session: DBSession): O
}
