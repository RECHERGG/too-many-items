package de.rechergg.toomanyitems.listener

import de.rechergg.toomanyitems.game.cache.ItemCache.addItems
import de.rechergg.toomanyitems.game.cache.ItemCache.removeItems
import net.axay.kspigot.event.listen
import org.bukkit.event.player.PlayerJoinEvent

object ConnectionListener {

    private val onJoin = listen<PlayerJoinEvent> {
        val player = it.player

        //TODO teleport

        removeItems(player)
        addItems(player)
    }
}