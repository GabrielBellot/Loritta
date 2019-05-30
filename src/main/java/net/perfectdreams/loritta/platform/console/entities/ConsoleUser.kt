package net.perfectdreams.loritta.platform.console.entities

import net.perfectdreams.loritta.api.entities.User

class ConsoleUser(override val name: String) : User {
    override val id: String
        get() = name.hashCode().toString()
    override val effectiveAvatarUrl: String
        get() = "https://cdn.discordapp.com/emojis/523176710439567392.png"
    override val avatarUrl: String?
        get() = "https://cdn.discordapp.com/emojis/523176710439567392.png"
    override val isBot: Boolean
        get() = false
    override val asMention: String
        get() = name
}