package de.rechergg.toomanyitems.game

import de.rechergg.toomanyitems.extension.cmp
import de.rechergg.toomanyitems.extension.items
import de.rechergg.toomanyitems.extension.plus
import de.rechergg.toomanyitems.game.GameFunction.start
import de.rechergg.toomanyitems.instance
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.taskRunLater
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import java.util.function.Function
import java.util.stream.Collectors

object GameManager {

    var gameState = GameState.PREPARING_WORLD
    val obtainedKey = NamespacedKey(instance, "invalidated")

    fun startGame(player: Player) {
        GameState.STARTING

        start(player)
    }

    fun stopGame() {
        gameState = GameState.ENDED
        collectWinners()

        taskRunLater(20 * 10) {
            Bukkit.shutdown()
        }
    }

    private fun collectWinners() {
        val places = onlinePlayers.stream().collect(Collectors.toMap(Function.identity()) { player ->
            onlinePlayers.count { firstPlayer -> firstPlayer.items.size == player.items.size }
        })

        places.values.distinct().map { it - 1 }.forEach { place ->
            val players = places.entries.filter { it.value == place }
            val winners = players.joinToString(", ") { it.key.name }
            val items = players.firstOrNull()?.key?.items?.size


            val component = when (place) {
                1 -> {
                    cmp("#1", NamedTextColor.GOLD) + cmp(" » ", NamedTextColor.DARK_GRAY) + cmp(winners.ifEmpty { "-" }) + cmp(" ($items)")
                }

                2 -> {
                    cmp("#2") + cmp(" » ", NamedTextColor.DARK_GRAY)  + cmp(winners.ifEmpty { "-" }) + cmp(" ($items)")
                }

                3 -> {
                    cmp("#3", NamedTextColor.RED) + cmp(" » ", NamedTextColor.DARK_GRAY) + cmp(winners.ifEmpty { "-" }) + cmp(" ($items)")
                }
                else -> null
            }

            component?.let { Bukkit.broadcast(it) }
            players.map { it.key }.forEach { player ->
                player.sendMessage(cmp("Du belegst Platz") + cmp("#$place", NamedTextColor.WHITE) + cmp("."))
            }
        }
    }


}
