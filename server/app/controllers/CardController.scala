package controllers

import infrastructure.impls.{F4DBSupport, MixInCardRepository, MixInEmployeeRepository}
import play.api.libs.json._
import javax.inject._
import play.api.libs.json.JsValue
import usecases.card.{GetAllCardsUseCase, SendCardUseCase, UsesGetAllCardsUseCase, UsesSendCardUseCase}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import scalikejdbc.DBSession
import usecases.dtos.input.{GetAllCardsInputDto, SendCardInputDto}
import usecases.dtos.output.GetAllCardsOutputDto

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class CardController @Inject()(cc: ControllerComponents)
  extends CardControllerTrait(cc)
    with MixInSendCardUseCase
    with MixInGetAllCardsUseCase {

  override def tx[M](f: DBSession => M): M = F4DBSupport.transaction(f)
}

abstract class CardControllerTrait(cc: ControllerComponents)
  extends AbstractController(cc)
    with UsesGetAllCardsUseCase
    with UsesSendCardUseCase {

  def tx[M](f: DBSession => M): M

  def getAll() = Action.async { implicit request: Request[AnyContent] =>
    Future {
      val dto: GetAllCardsOutputDto =
        tx { session =>
          getAllCardsUseCase.run(GetAllCardsInputDto())(session)
        }
      Ok(Json.toJson(dto))
    }
  }

  def sendCard() = Action(parse.json) { implicit request: Request[JsValue] =>
    tx { session =>
      request
        .body
        .validate[SendCardInputDto]
        .fold(
          _ => BadRequest("Illegal arguments"),
          params => {
            sendCardUseCase.run(params)(session).createdCardId.fold(
              InternalServerError("Failed sending card")
            )(cardId =>
              Ok(Json.obj("createdId" -> cardId.toString))
            )
          }
        )
    }
  }
}

trait MixInGetAllCardsUseCase {
  val getAllCardsUseCase: GetAllCardsUseCase = new GetAllCardsUseCase with MixInCardRepository
}

trait MixInSendCardUseCase {
  val sendCardUseCase: SendCardUseCase = new SendCardUseCase with MixInCardRepository with MixInEmployeeRepository
}
