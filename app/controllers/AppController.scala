package controllers

import javax.inject._

import play.api._
import play.api.Logger
import play.api.mvc.{Action, _}

import scala.concurrent._
import ExecutionContext.Implicits.global

@Singleton
class AppController @Inject()(env: Environment, cc: ControllerComponents) extends AbstractController(cc) {
  private lazy val logger = Logger(getClass)

  def index = Action.async { request: Request[AnyContent] =>
    if (request.path.endsWith("/")) {
      at("index.html").apply(request)
    } else {
      Future(Redirect(request.path + "/"))
    }
  }

  private[controllers] def assetHandler(file: String): Action[AnyContent] = Action { request: Request[AnyContent] =>
    val targetFile = env.getFile("ui/dist/" + file);

    if (targetFile.exists()) {
      if (targetFile.isFile) {
        Ok.sendFile(targetFile, inline = true).withHeaders(CACHE_CONTROL -> "no-store")
      } else {
        Forbidden(views.html.defaultpages.unauthorized())
      }
    } else {
      NotFound("404 - Page not found error\n" + request.path)
    }

  }

  def at(file: String): Action[AnyContent] = assetHandler(file)

}
