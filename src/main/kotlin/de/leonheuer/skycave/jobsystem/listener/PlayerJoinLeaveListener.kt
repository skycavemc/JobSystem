package de.leonheuer.skycave.jobsystem.listener

import de.leonheuer.skycave.jobsystem.JobSystem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerJoinLeaveListener(private val main: JobSystem): Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        main.playerManager.npcSetMode.remove(event.player.uniqueId)
        main.dataManager.registerUser(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        main.dataManager.unregisterUser(event.player.uniqueId)
    }

    @EventHandler
    fun onPlayerKick(event: PlayerKickEvent) {
        main.dataManager.unregisterUser(event.player.uniqueId)
    }

}