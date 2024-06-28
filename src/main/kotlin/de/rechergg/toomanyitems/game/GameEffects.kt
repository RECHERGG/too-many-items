package de.rechergg.toomanyitems.game

import de.rechergg.toomanyitems.extension.cmp
import de.rechergg.toomanyitems.extension.plus
import de.rechergg.toomanyitems.extension.prefix
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.extensions.onlinePlayers
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

object GameEffects {

    fun sendStartMessage(player: Player) {
        player.sendMessage(prefix() + cmp("Du hast das Spiel erfolgreich gestartet.", NamedTextColor.GREEN))

        onlinePlayers.filter { it.uniqueId != player.uniqueId }.forEach {
            it.sendMessage(
                prefix() + cmp("Das Spiel wurde von ") + cmp(
                    player.name,
                    NamedTextColor.GREEN
                ) + cmp(" gestartet!")
            )
        }
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
    }

    fun sendStartingMessage(player: Player,time: Long) {
        when (time) {
            1L, 2L, 3L, 5L, 10L -> {
                player.sendMessage(
                    prefix() + cmp("Erkundungszeit beginnt in ") + cmp(
                        "$time Sekunde${if (time == 1L) "" else "n"}",
                        NamedTextColor.GOLD
                    ) + cmp("!")
                )
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f)
            }
        }
    }

    fun sendStartingTitle(time: Long) {
        onlinePlayers.forEach {
            it.title(
                cmp("Sammelwahn", NamedTextColor.GOLD),
                cmp("Das Spiel startet in $time ${if (time != 1L) "Sekunden" else "Sekunde"}!"),
                Duration.ZERO,
                2.seconds.toJavaDuration(),
                Duration.ZERO
            )
        }
    }
}