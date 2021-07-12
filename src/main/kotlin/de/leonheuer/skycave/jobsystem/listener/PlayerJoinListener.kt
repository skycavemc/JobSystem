package de.leonheuer.skycave.jobsystem.listener

import de.leonheuer.skycave.jobsystem.JobSystem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener(private val main: JobSystem): Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        main.playerManager.npcSetMode.remove(event.player.uniqueId)
    }

}