package de.rechergg.toomanyitems.listener

import net.axay.kspigot.event.listen
import org.bukkit.event.entity.FoodLevelChangeEvent

object PlayerHealthListener {

    private val onHealth = listen<FoodLevelChangeEvent> {
        it.isCancelled = true
    }
}