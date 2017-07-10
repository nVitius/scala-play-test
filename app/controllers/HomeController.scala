package controllers

import javax.inject._

import java.net.UnknownHostException
import akka.http.scaladsl.model.headers.CacheDirectives.public
import com.fasterxml.jackson.databind.JsonNode
import play.api._
import play.api.mvc._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.jsoup.HttpStatusException
import play.api.libs.json.{JsValue, Json}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.obj("status" -> 200))
  }

  def fetchTitle() = Action(parse.json) { request =>
    val browser = JsoupBrowser()
    try {
      val doc = browser.get((request.body \ "site").as[String])
      Ok(Json.obj("status" -> 200, "title" -> doc.title))
    } catch {
      case unkHostE: UnknownHostException => BadRequest(Json.obj("status" -> 400, "message" -> "Invalid host"))
      case httpE: HttpStatusException => BadRequest(Json.obj("status" -> 400, "message" -> "Error scraping page"))
    }
  }

}
