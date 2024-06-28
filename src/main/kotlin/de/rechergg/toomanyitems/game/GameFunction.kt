package de.rechergg.toomanyitems.game

import de.rechergg.toomanyitems.extension.cmp
import de.rechergg.toomanyitems.extension.items
import de.rechergg.toomanyitems.extension.spectator
import de.rechergg.toomanyitems.game.GameEffects.sendStartMessage
import de.rechergg.toomanyitems.game.GameEffects.sendStartingMessage
import de.rechergg.toomanyitems.game.GameEffects.sendStartingTitle
import de.rechergg.toomanyitems.game.GameManager.gameState
import de.rechergg.toomanyitems.game.GameManager.obtainedKey
import de.rechergg.toomanyitems.game.GameManager.stopGame
import de.rechergg.toomanyitems.world.WorldManager.changeWorldBoarder
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.sync
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object GameFunction {

    private var remaining = 4.minutes

    fun start(player: Player) {
        sendStartMessage(player)

        task(false, 20, 20, 10) { task ->
            val time = task.counterDownToOne!!

            onlinePlayers.forEach {
                sendStartingMessage(it, time)
            }

            if (task.counterDownToZero == 0L) {
                sync {
                    changeWorldBoarder()
                }
                startRound()
                return@task
            }
        }
    }

    private fun startRound() {
        task(false, 20, 20, 10) { task ->
            sendStartingTitle(task.counterDownToOne!!)

            if (task.counterDownToZero == 0L) {
                // game has startet here
                gameState = GameState.PLAYING
                beginGame()
                return@task
            }
        }
    }

    private fun beginGame() {
        task(false, 20, 20) { task ->
            remaining -= 1.seconds

            if (remaining == Duration.ZERO && !task.isCancelled) {
                stopGame()
                task.cancel()
                return@task
            }
        }

        task(false, 1, 1) { task ->
            tick()

            if (gameState != GameState.PLAYING && !task.isCancelled) {
                task.cancel()
            }
        }
    }

    private fun tick() {
        onlinePlayers.filter { !it.spectator }.forEach { player ->
            loop(player)
        }
    }

    private fun loop(player: Player) {
        var items = 0

        player.inventory.contents.filterNotNull().forEach {
            if (it.isEmpty) return@forEach
            if (it.itemMeta != null && it.itemMeta?.persistentDataContainer?.get(obtainedKey, PersistentDataType.BOOLEAN) == true) return@forEach
            if (player.items.contains(it.type)) return@forEach
            player.items.add(it.type)
            items++
        }

        if (items != 0) {
            println(player.items.joinToString(", "))
            player.title(cmp("+$items", NamedTextColor.GREEN))
            items = 0
        }
    }

}