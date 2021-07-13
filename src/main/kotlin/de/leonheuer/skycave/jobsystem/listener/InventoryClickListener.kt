package de.leonheuer.skycave.jobsystem.listener

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.Job
import de.leonheuer.skycave.jobsystem.enums.Message
import de.leonheuer.skycave.jobsystem.util.Util
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.time.Duration
import java.time.LocalDateTime

class InventoryClickListener(private val main: JobSystem): Listener {

    @Suppress("Deprecation")
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked
        if (player !is Player) {
            return
        }
        val item = event.currentItem
        if (item == null || item.type.isAir) {
            return
        }

        when (player.openInventory.title) {
            "§3§lJob §6Auswahl" -> {
                event.isCancelled = true
                val job = Job.fromItemStack(item) ?: return

                val user = main.dataManager.getUser(player.uniqueId)
                if (user == null) {
                    main.dataManager.createUser(player.uniqueId, job)
                    player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString().replace("%job", job.friendlyName).get())
                    Util.openSelector(player)
                    player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
                    return
                }

                if (Duration.between(user.jobChangeDate, LocalDateTime.now()).toHours() < 48) {
                    player.sendMessage(Message.JOB_CHANGE_WAIT.getString().get())
                    player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 0.7f)
                    return
                }

                user.job = job
                user.jobChangeDate = LocalDateTime.now()
                player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString().replace("%job", job.friendlyName).get())
                Util.openSelector(player)
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
            }
            "§6Item Ankauf" -> {
                event.isCancelled = true
            }
        }
    }

}