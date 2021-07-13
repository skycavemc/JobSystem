package de.leonheuer.skycave.jobsystem.listener

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.GUIView
import de.leonheuer.skycave.jobsystem.enums.Message
import de.leonheuer.skycave.jobsystem.model.NPC
import de.leonheuer.skycave.jobsystem.util.Util
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class PlayerInteractListener(private val main: JobSystem): Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val entity = event.rightClicked
        val location = entity.location.toBlockLocation()
        val list = main.playerManager.npcSetMode

        if (list.contains(player.uniqueId)) {
            main.dataManager.npc = NPC(location, entity.type)
            player.sendMessage(Message.JOB_ADMIN_SET_NPC_SUCCESS.getString()
                .replace("%type", entity.type.toString().lowercase())
                .replace("%x", location.x.toString())
                .replace("%y", location.y.toString())
                .replace("%z", location.z.toString())
                .get()
            )
            main.dataManager.saveNPC()
            list.remove(player.uniqueId)
            return
        }

        val npc = main.dataManager.npc ?: return
        if (location.toVector() == npc.location.toVector() && entity.type == npc.entityType) {
            player.playSound(player.location, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f, 1.0f)
            Util.openGUI(player, GUIView.JOBS)
        }
    }

}