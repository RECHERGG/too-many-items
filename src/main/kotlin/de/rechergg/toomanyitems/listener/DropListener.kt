package de.rechergg.toomanyitems.listener

import de.rechergg.toomanyitems.TooManyItems.Companion.instance
import de.rechergg.toomanyitems.game.GameManager.gameState
import de.rechergg.toomanyitems.game.GameState
import de.rechergg.toomanyitems.game.cache.ItemCache
import net.axay.kspigot.event.listen
import net.axay.kspigot.items.meta
import org.bukkit.NamespacedKey
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.persistence.PersistentDataType

object DropListener {

    val dropKey = NamespacedKey(instance, "drop")

    private val onEntityDeath = listen<EntityDeathEvent> {
        val killer = it.entity.killer ?: return@listen

        it.drops.forEach { drop ->
            drop.meta {
                persistentDataContainer.set(dropKey, PersistentDataType.STRING, killer.uniqueId.toString())
            }
        }
    }

    private val onBreak = listen<BlockBreakEvent> {
        if (gameState != GameState.PLAYING) {
            it.isCancelled = true
            return@listen
        }

        val player = it.player
        val block = it.block

        val drops = block.getDrops(player.inventory.itemInMainHand)

        it.isDropItems = false
        drops.forEach { drop ->
            drop.itemMeta = drop.itemMeta?.apply {
                persistentDataContainer.set(dropKey, PersistentDataType.STRING, player.uniqueId.toString())
            }
            block.world.dropItemNaturally(block.location, drop)
        }
    }

    private val onDrop = listen<PlayerDropItemEvent> {
        if (it.itemDrop.itemStack in ItemCache.items) {
            it.isCancelled = true
        }
    }
}