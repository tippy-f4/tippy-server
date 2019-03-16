package controllers

import domain.repositories.EmployeeRepository
import infrastructure.impls.{ F4DBSupport, MixInEmployeeRepository }
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc.{ AbstractController, AnyContent, ControllerComponents, Request }
import scalikejdbc.DBSession
import usecases.dtos.input.GetAllEmployeesInputDto
import usecases.employee.{ GetAllEmployeesUseCase, GetEmployeeByIdUseCase, UsesGetAllEmployeesUseCase, UsesGetEmployeeByIdUseCase }

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class EmployeeController @Inject()(cc: ControllerComponents) extends EmployeeControllerTrait(cc)
  with MixInGetAllEmployeesUseCase
  with MixInGetEmployeeByIdUseCase {
  override def tx[M](f: DBSession => M): M = F4DBSupport.transaction(f)
}

abstract class EmployeeControllerTrait(cc: ControllerComponents) extends AbstractController(cc)
  with UsesGetAllEmployeesUseCase
  with UsesGetEmployeeByIdUseCase {

  def tx[M](f: DBSession => M): M

  def getAll() = Action.async { implicit request: Request[AnyContent] =>
    Future {
      val dto =
        tx { session =>
          getAllEmployeesUseCase.run(GetAllEmployeesInputDto())(session)
        }
      Ok(Json.toJson(dto))
    }
  }


}

trait MixInGetAllEmployeesUseCase {
  val getAllEmployeesUseCase: GetAllEmployeesUseCase = new GetAllEmployeesUseCase with MixInEmployeeRepository
}

trait MixInGetEmployeeByIdUseCase {
  val getEmployeeByIdUseCase: GetEmployeeByIdUseCase = new GetEmployeeByIdUseCase with MixInEmployeeRepository
}