package de.rechergg.toomanyitems.game.cache

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ItemCache {

    val axe = ItemStack(Material.IRON_AXE)
    val pickaxe = ItemStack(Material.IRON_PICKAXE)
    val shovel = ItemStack(Material.IRON_SHOVEL)
    val hoe = ItemStack(Material.IRON_HOE)

    fun addItems(player: Player) {
        player.apply {
            inventory.setItem(0, axe)
            inventory.setItem(1, pickaxe)
            inventory.setItem(2, shovel)
            inventory.setItem(3, hoe)
        }
    }

    fun removeItems(player: Player) {
        player.apply {
            inventory.remove(axe)
            inventory.remove(pickaxe)
            inventory.remove(shovel)
            inventory.remove(hoe)
        }
    }

}