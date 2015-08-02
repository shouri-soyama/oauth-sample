package controllers

import java.math.BigInteger
import java.security.SecureRandom
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{Ok, ScalatraServlet}
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeRequestUrl, GoogleAuthorizationCodeFlow}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson.JacksonFactory
import com.google.api.services.oauth2.Oauth2
import com.typesafe.config.ConfigFactory

class ApiServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  val conf =  ConfigFactory.load()
  get("/goauth") {
    val state = new BigInteger(130, new SecureRandom()).toString(32)
    val url = new GoogleAuthorizationCodeRequestUrl(
      conf.getString("google.client_id"),
      conf.getString("google.callback_url"),
      conf.getStringList("google.scopes")
    ).setState(state).toURL.toString
    redirect(url)
  }

  get("/goauth2callback") {
    params.get("code") match {
      case Some(code) => {
        val flow = new GoogleAuthorizationCodeFlow(
          new NetHttpTransport(),
          new JacksonFactory(),
          conf.getString("google.client_id"),
          conf.getString("google.client_secret"),
          conf.getStringList("google.scopes"))
        val tokenResponse = flow.newTokenRequest(code)
          .setRedirectUri(conf.getString("google.callback_url")).execute()
        val credential = flow.createAndStoreCredential(tokenResponse, null)

        val oauth2 = new Oauth2.Builder(
          credential.getTransport,
          credential.getJsonFactory,
          credential)
          .setApplicationName(conf.getString("google.application_name")).build()

        oauth2.userinfo().get().execute()
        redirect("/static/index.html")
      }
      case None => redirect("/static/somethingbad.html")

    }
  }
}
