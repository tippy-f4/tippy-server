package controllers

import javax.inject._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsValue, Reads}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import scalikejdbc.DBSession
import usecases.card.{UsesGetAllCardsUseCase, UsesSendCardUseCase}
import usecases.dtos.input.{GetAllCardsInputDto, SendCardInputDto}
import usecases.dtos.output.GetAllCardsOutputDto

import scala.concurrent.ExecutionContext

@Singleton
class CardController @Inject()(cc: ControllerComponents) extends AbstractController(cc)

abstract class CardControllerTrait(cc: ControllerComponents)
  extends AbstractController(cc)
    with UsesGetAllCardsUseCase
    with UsesSendCardUseCase {

  implicit val ec: ExecutionContext
  def tx[M](f: DBSession => M): M

  def getAll() = Action { implicit request: Request[AnyContent] =>
    val dto: GetAllCardsOutputDto =
      tx { session =>
        getAllCardsUseCase.run(GetAllCardsInputDto())(session)
      }
    Ok(dto)
  }

  implicit val sendCardJsonReader: Reads[SendCardInputDto] = (
    (__ \ "employee_id").read[String] and
      (__ \ "message").read[String]
  )(SendCardInputDto.apply _)

  def sendCard() = Action(parse.json) { implicit request: Request[JsValue] =>
    tx { session =>
      request
        .body
        .validate[SendCardInputDto]
        .fold(
          _ => BadRequest("Illegal arguments"),
          params => {
            Ok(sendCardUseCase.run(params)(session))
          }
        )
    }
  }
}