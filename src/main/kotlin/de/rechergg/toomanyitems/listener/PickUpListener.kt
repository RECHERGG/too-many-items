package de.rechergg.toomanyitems.listener

import de.rechergg.toomanyitems.listener.DropListener.dropKey
import net.axay.kspigot.event.listen
import org.bukkit.event.player.PlayerAttemptPickupItemEvent
import org.bukkit.persistence.PersistentDataType
import java.util.*

object PickUpListener {

    private val onPickUp = listen<PlayerAttemptPickupItemEvent> {
        val player = it.player
        val itemStack = it.item.itemStack
        if (itemStack.itemMeta?.persistentDataContainer?.has(dropKey) == false) {
            it.isCancelled = true
            return@listen
        }

        if (UUID.fromString(itemStack.itemMeta.persistentDataContainer.get(dropKey, PersistentDataType.STRING)) != player.uniqueId) {
            it.isCancelled = true
        }
    }
}