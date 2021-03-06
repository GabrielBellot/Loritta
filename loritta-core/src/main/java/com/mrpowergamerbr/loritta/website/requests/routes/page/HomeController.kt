package com.mrpowergamerbr.loritta.website.requests.routes.page

import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.website.LoriRequiresVariables
import com.mrpowergamerbr.loritta.website.evaluate
import kotlinx.coroutines.runBlocking
import net.perfectdreams.loritta.utils.FeatureFlags
import net.perfectdreams.loritta.website.LorittaWebsite
import net.perfectdreams.loritta.website.utils.ScriptingUtils
import org.jooby.Request
import org.jooby.Response
import org.jooby.Route
import org.jooby.mvc.GET
import org.jooby.mvc.Path
import java.io.File
import kotlin.reflect.full.createType

@Path("/:localeId")
class HomeController {
	@GET
	@LoriRequiresVariables(true)
	fun handle(req: Request, res: Response, chain: Route.Chain, localeId: String) {
		if (localeId == "translation") {
			chain.next(req, res)
			return
		}

		val variables = req.get<MutableMap<String, Any?>>("variables")

		if (FeatureFlags.NEW_WEBSITE_PORT && FeatureFlags.isEnabled(FeatureFlags.Names.NEW_WEBSITE_PORT + "-home")) {
			val html = runBlocking {
				ScriptingUtils.evaluateWebPageFromTemplate(
						File(
								"${LorittaWebsite.INSTANCE.config.websiteFolder}/views/home.kts"
						),
						mapOf(
								"path" to req.path().split("/").drop(2).joinToString("/"),
								"websiteUrl" to LorittaWebsite.INSTANCE.config.websiteUrl,
								"locale" to ScriptingUtils.WebsiteArgumentType(BaseLocale::class.createType(nullable = false), variables["locale"]!!)
						)
				)
			}

			res.send(html)
		} else {
			res.send(evaluate("home.html", variables))
		}
	}
}