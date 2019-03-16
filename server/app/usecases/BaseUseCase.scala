package usecases

import scalikejdbc.DBSession
import usecases.dtos.{ InputDto, OutputDto }

trait BaseUseCase[I <: InputDto, O <: OutputDto] {
  def run(inputDto: I)(implicit session: DBSession): O
}
