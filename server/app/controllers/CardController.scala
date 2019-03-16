package controllers

import javax.inject._
import play.api.mvc.{ AbstractController, AnyContent, ControllerComponents, Request }
import scalikejdbc.DBSession
import usecases.card.UsesGetAllCardsUseCase
import usecases.dtos.input.GetAllCardsInputDto
import usecases.dtos.output.GetAllCardsOutputDto

import scala.concurrent.ExecutionContext

@Singleton
class CardController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

}

abstract class CardControllerTrait(cc: ControllerComponents) extends AbstractController(cc) with UsesGetAllCardsUseCase {

  implicit val ec: ExecutionContext
  def tx[M](f: DBSession => M): M

  def getAll() = Action { implicit request: Request[AnyContent] =>
    val dto: GetAllCardsOutputDto =
      tx { session =>
        getAllCardsUseCase.run(GetAllCardsInputDto())(session)
      }
    Ok(dto)
  }
}