package net.perfectdreams.loritta.website.routes

import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.request.path
import io.ktor.response.respondText
import net.perfectdreams.loritta.utils.locale.BaseLocale
import net.perfectdreams.loritta.website.LorittaWebsite
import net.perfectdreams.loritta.website.utils.ScriptingUtils
import java.io.File

class SupportRoute : LocalizedRoute("/support") {
    override suspend fun onLocalizedRequest(call: ApplicationCall, locale: BaseLocale) {
        val html = ScriptingUtils.evaluateWebPageFromTemplate(
                File(
                        "${LorittaWebsite.INSTANCE.config.websiteFolder}/views/support.kts"
                ),
                mapOf(
                        "path" to call.request.path().split("/").drop(2).joinToString("/"),
                        "websiteUrl" to LorittaWebsite.INSTANCE.config.websiteUrl,
                        "locale" to locale
                )
        )

        call.respondText(html, ContentType.Text.Html)
    }
}