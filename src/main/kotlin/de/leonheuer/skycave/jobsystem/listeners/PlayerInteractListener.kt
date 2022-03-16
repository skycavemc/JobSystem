package de.leonheuer.skycave.jobsystem.listeners

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.CustomSound
import de.leonheuer.skycave.jobsystem.enums.GUIView
import de.leonheuer.skycave.jobsystem.enums.Message
import de.leonheuer.skycave.jobsystem.util.Utils
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class PlayerInteractListener(private val main: JobSystem): Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val entity = event.rightClicked
        val location = entity.location.toBlockLocation()

        if (main.npcSetMode.contains(player.uniqueId)) {
            main.config.set("npc_location", location)
            main.config.set("npc_type", entity.type.toString())
            player.sendMessage(Message.JOB_ADMIN_SET_NPC_SUCCESS.getString()
                .replace("%type", entity.type.toString().lowercase())
                .replace("%x", location.x.toString())
                .replace("%y", location.y.toString())
                .replace("%z", location.z.toString())
                .get()
            )
            main.npcSetMode.remove(player.uniqueId)
            return
        }

        val loc = main.config.getSerializable("npc_location", Location::class.java) ?: return
        val type: EntityType
        try {
            val key = main.config.getString("npc_type") ?: return
            type = EntityType.valueOf(key)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            return
        }

        if (location.world == loc.world && location.distance(loc) == 0.0 && entity.type == type) {
            CustomSound.VILLAGER_OPEN.playTo(player)
            Utils.openGUI(player, GUIView.JOBS)
        }
    }

}