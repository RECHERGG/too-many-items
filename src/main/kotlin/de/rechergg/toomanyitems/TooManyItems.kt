package de.rechergg.toomanyitems

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.axay.kspigot.main.KSpigot

class TooManyItems: KSpigot() {

    companion object {
        lateinit var instance: TooManyItems
    }

    override fun load() {
        instance = this
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
    }

    override fun startup() {
        CommandAPI.onEnable()
    }

    override fun shutdown() {
        CommandAPI.onDisable()
    }

}

val instance by lazy {
    TooManyItems.instance
}