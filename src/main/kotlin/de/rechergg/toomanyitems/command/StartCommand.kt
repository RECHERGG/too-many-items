package de.rechergg.toomanyitems.command

import de.rechergg.toomanyitems.extension.cmp
import de.rechergg.toomanyitems.extension.plus
import de.rechergg.toomanyitems.extension.prefix
import de.rechergg.toomanyitems.game.GameManager.gameState
import de.rechergg.toomanyitems.game.GameManager.startGame
import de.rechergg.toomanyitems.game.GameState
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import net.kyori.adventure.text.format.NamedTextColor

object StartCommand {

    val command = commandTree("start") {
        withPermission("too-many-items.start")
        playerExecutor { player, _ ->
            if (gameState != GameState.WAITING) {
                player.sendMessage(prefix() + cmp("Das Spiel wurde bereits gestartet!", NamedTextColor.RED))
                return@playerExecutor
            }

            startGame(player)
        }
    }
}