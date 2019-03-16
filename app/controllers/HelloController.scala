package controllers

import javax.inject._
import play.api.mvc.{ AbstractController, AnyContent, ControllerComponents, Request }

@Singleton
case class HelloController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def health() = Action { implicit request: Request[AnyContent] =>
    Ok("hello")
  }
}
