package de.rechergg.toomanyitems.extension

import de.rechergg.toomanyitems.TooManyItems.Companion.instance
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.sync
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID

val playerItems = mutableMapOf<UUID, MutableList<Material>>()
val spectatorPlayers = mutableListOf<UUID>()

val Player.items: MutableList<Material>
    get() {
        if (playerItems[uniqueId] == null) {
            playerItems[uniqueId] = mutableListOf()
        }

        return playerItems[uniqueId]!!
    }

var Player.spectator: Boolean
    get() = spectatorPlayers.contains(uniqueId)
    set(value) {
        if (value) {
            spectatorPlayers.add(uniqueId)
            //inventory.setItem(0, ItemsCache.compass)
            allowFlight = true

            onlinePlayers.filter { it.uniqueId != uniqueId }.forEach {
                sync {
                    it.hidePlayer(instance, this)
                }
            }

            onlinePlayers.filter { it.spectator }.forEach {
                sync {
                    hidePlayer(instance, it)
                }
            }
        } else {
            spectatorPlayers.remove(uniqueId)
        }
    }