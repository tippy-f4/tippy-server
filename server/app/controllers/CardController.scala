package controllers

import infrastructure.impls.{ F4DBSupport, MixInCardRepository }
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc.{ AbstractController, AnyContent, ControllerComponents, Request }
import scalikejdbc.DBSession
import usecases.card.{ GetAllCardsUseCase, UsesGetAllCardsUseCase }
import usecases.dtos.input.GetAllCardsInputDto
import usecases.dtos.output.GetAllCardsOutputDto

@Singleton
class CardController @Inject()(cc: ControllerComponents) extends CardControllerTrait(cc) with MixInCardRepository with MixInGetAllCardsUseCase {
  override def tx[M](f: DBSession => M): M = F4DBSupport.transaction(f)
}

abstract class CardControllerTrait(cc: ControllerComponents) extends AbstractController(cc) with UsesGetAllCardsUseCase {

  def tx[M](f: DBSession => M): M

  def getAll() = Action { implicit request: Request[AnyContent] =>
    val dto: GetAllCardsOutputDto =
      tx { session =>
        getAllCardsUseCase.run(GetAllCardsInputDto())(session)
      }
    Ok(Json.toJson(dto))
  }
}

trait MixInGetAllCardsUseCase {
  val getAllCardsUseCase: GetAllCardsUseCase = new GetAllCardsUseCase with MixInCardRepository
}