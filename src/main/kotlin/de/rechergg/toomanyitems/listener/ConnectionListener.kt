package de.rechergg.toomanyitems.listener

import de.rechergg.toomanyitems.game.cache.ItemCache.addItems
import de.rechergg.toomanyitems.game.cache.ItemCache.removeItems
import de.rechergg.toomanyitems.world.WorldManager.teleport
import net.axay.kspigot.event.listen
import org.bukkit.event.player.PlayerJoinEvent

object ConnectionListener {

    private val onJoin = listen<PlayerJoinEvent> {
        val player = it.player

        teleport(player)

        removeItems(player)
        addItems(player)
    }
}