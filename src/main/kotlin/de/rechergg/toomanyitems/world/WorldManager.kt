package de.rechergg.toomanyitems.world

import de.rechergg.toomanyitems.game.GameManager.gameState
import de.rechergg.toomanyitems.game.GameState
import net.axay.kspigot.extensions.worlds
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.block.Biome
import org.bukkit.entity.Player

object WorldManager {

    private var world = worlds.first()
    private var biome = getRandomBiome()

    init {
        world.apply {
            time = 6000

            setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
            setGameRule(GameRule.FALL_DAMAGE, false)
            setGameRule(GameRule.FIRE_DAMAGE, false)
            setGameRule(GameRule.DISABLE_RAIDS, false)
            setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false)
            setGameRule(GameRule.DO_TRADER_SPAWNING, false)
            setGameRule(GameRule.DO_WEATHER_CYCLE, false)
            setGameRule(GameRule.KEEP_INVENTORY, true)
        }

        var biomeLocation = getLocation(biome)
        while (biomeLocation == null) {
            biome = getRandomBiome()
            biomeLocation = getLocation(biome)
        }

        world.worldBorder.apply {
            setCenter(biomeLocation.x, biomeLocation.z)
            size = 10.0
        }

        // set GameState to WAITING so people can join
        gameState = GameState.WAITING
    }

    fun teleport(player: Player) {
        player.teleportAsync(getLocation(biome)!!.toHighestLocation().clone().add(0.0, 1.0, 0.0))
    }

    fun changeWorldBoarder() {
        world.worldBorder.apply {
            size = 500.0
        }
    }

    private fun getLocation(biome: Biome): Location? = world.locateNearestBiome(Location(world, 0.0, 0.0, 0.0), 25000, biome)?.location

    private fun getRandomBiome(): Biome = Biome.valueOf(WorldBiome.entries.random().toString())
}