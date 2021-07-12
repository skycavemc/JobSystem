package de.leonheuer.skycave.jobsystem.listener

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.Message
import de.leonheuer.skycave.jobsystem.model.NPC
import de.leonheuer.skycave.jobsystem.util.Util
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class PlayerInteractListener(private val main: JobSystem): Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val entity = event.rightClicked
        if (main.playerManager.npcSetMode.contains(player.uniqueId)) {
            main.dataManager.npc = NPC(entity.location, entity.type)
            player.sendMessage(Message.JOB_ADMIN_SET_NPC_SUCCESS.string
                .replace("%type", entity.type.toString().lowercase())
                .replace("%x", entity.location.x.toString())
                .replace("%y", entity.location.y.toString())
                .replace("%z", entity.location.z.toString())
                .addPrefix().get()
            )
            main.dataManager.saveNPC()
            return
        }

        val npc = main.dataManager.npc ?: return
        if (entity.location == npc.location && entity.type == npc.entityType) {
            Util.openShop(player)
        }
    }

}