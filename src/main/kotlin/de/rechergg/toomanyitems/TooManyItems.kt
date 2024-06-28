package de.rechergg.toomanyitems

import de.rechergg.toomanyitems.command.StartCommand
import de.rechergg.toomanyitems.game.GameManager
import de.rechergg.toomanyitems.listener.*
import de.rechergg.toomanyitems.world.WorldManager
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.axay.kspigot.main.KSpigot

class TooManyItems: KSpigot() {

    companion object {
        lateinit var instance: TooManyItems
    }

    override fun load() {
        instance = this

        //val worldFolder = File("./world")
        //if (worldFolder.exists()) worldFolder.deleteRecursively()

        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
    }

    override fun startup() {
        CommandAPI.onEnable()

        GameManager
        WorldManager

        StartCommand

        ConnectionListener
        CraftListener
        DropListener
        PickUpListener
        DamageListener
    }

    override fun shutdown() {
        CommandAPI.onDisable()
    }

}

val instance by lazy {
    TooManyItems.instance
}