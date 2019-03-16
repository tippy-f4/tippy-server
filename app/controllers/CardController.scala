package controllers

import infrastructure.impls.{ F4DBSupport, MixInCardRepository, MixInEmployeeRepository }
import play.api.libs.json._
import javax.inject._
import play.api.libs.json.JsValue
import usecases.card._
import play.api.mvc.{ AbstractController, AnyContent, ControllerComponents, Request }
import scalikejdbc.DBSession
import usecases.dtos.input.{ GetAllCardsInputDto, GetCardsByEmployeeIdInputDto, SendCardInputDto }
import usecases.dtos.output.{ GetAllCardsOutputDto, SendCardOutputDto }

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class CardController @Inject()(cc: ControllerComponents)
  extends CardControllerTrait(cc)
    with MixInSendCardUseCase
    with MixInGetAllCardsUseCase
    with MixInGetCardsByEmployeeIdUseCase {

  override def tx[M](f: DBSession => M): M = F4DBSupport.transaction(f)
}

abstract class CardControllerTrait(cc: ControllerComponents)
  extends AbstractController(cc)
    with UsesGetAllCardsUseCase
    with UsesSendCardUseCase
    with UsesGetCardsByEmployeeIdUseCase {

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

  def sendCard() = Action.async(parse.json) { implicit request: Request[JsValue] =>
    Future {
      tx { session =>
        request.body.validate[SendCardInputDto].fold(
          _ => BadRequest("Illegal arguments"),
          params => {
            val outputDto = sendCardUseCase.run(params)(session)
            Ok(Json.toJson(outputDto))
          }
        )
      }
    }
  }

  def getByEmployeeId(employeeId: String) = Action.async { implicit request: Request[AnyContent] =>
    Future {
      val outputDto =
        tx { session =>
          getCardsByEmployeeIdUseCase.run(GetCardsByEmployeeIdInputDto(employeeId))(session)
        }
      Ok(Json.toJson(outputDto))
    }
  }
}

trait MixInGetAllCardsUseCase {
  val getAllCardsUseCase: GetAllCardsUseCase = new GetAllCardsUseCase with MixInCardRepository
}

trait MixInSendCardUseCase {
  val sendCardUseCase: SendCardUseCase = new SendCardUseCase with MixInCardRepository with MixInEmployeeRepository
}

trait MixInGetCardsByEmployeeIdUseCase {
  val getCardsByEmployeeIdUseCase: GetCardsByEmployeeIdUseCase = new GetCardsByEmployeeIdUseCase with MixInCardRepository with MixInEmployeeRepository
}

