package de.rechergg.toomanyitems.listener

import de.rechergg.toomanyitems.extension.cmp
import de.rechergg.toomanyitems.game.GameManager.obtainedKey
import net.axay.kspigot.event.listen
import net.axay.kspigot.items.meta
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.persistence.PersistentDataType

object CraftListener {

    private val onCraft = listen<CraftItemEvent> {
        val currentItem = it.currentItem ?: return@listen

        currentItem.meta {
            itemName(cmp("Gefertigte Gegenstände zählen nicht", NamedTextColor.RED))
            persistentDataContainer.set(obtainedKey, PersistentDataType.BOOLEAN, true)
        }

    }
}