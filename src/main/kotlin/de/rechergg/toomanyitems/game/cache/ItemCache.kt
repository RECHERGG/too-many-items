package de.rechergg.toomanyitems.game.cache

import de.rechergg.toomanyitems.game.GameManager.obtainedKey
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

object ItemCache {

    private val axe = itemStack(Material.IRON_AXE) {
        meta {
            persistentDataContainer.set(obtainedKey, PersistentDataType.BOOLEAN, true)
            isUnbreakable = true
        }
    }
    private val pickaxe = itemStack(Material.IRON_PICKAXE) {
        meta {
            persistentDataContainer.set(obtainedKey, PersistentDataType.BOOLEAN, true)
            isUnbreakable = true
        }
    }
    private val shovel = itemStack(Material.IRON_SHOVEL) {
        meta {
            persistentDataContainer.set(obtainedKey, PersistentDataType.BOOLEAN, true)
            isUnbreakable = true
        }
    }
    private val hoe = itemStack(Material.IRON_HOE) {
        meta {
            persistentDataContainer.set(obtainedKey, PersistentDataType.BOOLEAN, true)
            isUnbreakable = true
        }
    }

    val items = setOf(axe, pickaxe, shovel, hoe)

    fun addItems(player: Player) {
        player.apply {
            inventory.setItem(0, axe)
            inventory.setItem(1, pickaxe)
            inventory.setItem(2, shovel)
            inventory.setItem(3, hoe)
        }
    }

    fun removeItems(player: Player) {
        player.inventory.clear()
    }

}