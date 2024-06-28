package de.rechergg.toomanyitems.listener

import net.axay.kspigot.event.listen
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityDamageEvent

object DamageListener {

    private val onDamage = listen<EntityDamageEvent> {
        if (it.entityType == EntityType.PLAYER) {
            it.isCancelled = true
        }
    }
}