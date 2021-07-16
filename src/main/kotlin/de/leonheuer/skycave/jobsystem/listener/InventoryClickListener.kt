package de.leonheuer.skycave.jobsystem.listener

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.GUIView
import de.leonheuer.skycave.jobsystem.enums.Job
import de.leonheuer.skycave.jobsystem.enums.Message
import de.leonheuer.skycave.jobsystem.enums.RequirementResult
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

        val title = player.openInventory.title
        if (title != GUIView.JOBS.getTitle() &&
            title != GUIView.SELL_PERSONAL.getTitle() &&
            title != GUIView.SELL.getTitle()
        ) {
            return
        }

        event.isCancelled = true

        when (item.itemMeta.displayName) {
            "§6Berufe" -> {
                player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                Util.openGUI(player, GUIView.JOBS)
                return
            }
            "§6Allgemeiner Ankauf" -> {
                player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                Util.openGUI(player, GUIView.SELL)
                return
            }
            "§6Persönlicher Ankauf" -> {
                player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                Util.openGUI(player, GUIView.SELL_PERSONAL)
                return
            }
        }

        when (player.openInventory.title) {
            GUIView.JOBS.getTitle() -> {
                val job = Job.fromItemStack(item) ?: return

                val user = main.dataManager.getUser(player.uniqueId)
                if (user == null) {
                    main.dataManager.createUser(player.uniqueId, job)
                    player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString().replace("%job", job.friendlyName).get())
                    Util.openGUI(player, GUIView.JOBS)
                    player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
                    return
                }

                if (Duration.between(user.jobChangeDate, LocalDateTime.now()).toHours() < 48) {
                    player.sendMessage(Message.JOB_CHANGE_WAIT.getString().get())
                    player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 0.7f)
                    return
                }

                when (Util.getRequirementResult(player, user)) {
                    RequirementResult.USE_FREE -> {
                        user.job = job
                        user.jobChangeDate = LocalDateTime.now()
                        user.freeJobChanges.dec()
                        player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString()
                            .replace("%job", job.friendlyName).get())
                        player.sendMessage(Message.JOB_CHANGE_USE_FREE.getString()
                            .replace("%amount", user.freeJobChanges.toString()).get())
                        Util.openGUI(player, GUIView.JOBS)
                        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
                    }
                    RequirementResult.PAY -> Util.openConfirmGUI(player, job)
                    RequirementResult.NO_MONEY -> {
                        player.sendMessage(Message.JOB_CHANGE_NO_MONEY.getString().get())
                        player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 0.7f)
                    }
                }
            }
            GUIView.SELL.getTitle() -> {
                TODO("sell item")
            }
            GUIView.SELL_PERSONAL.getTitle() -> {
                TODO("sell item")
            }
            GUIView.CONFIRM.getTitle() -> {
                val job = Util.extractJobFromItemMeta(item)
                val user = main.dataManager.getUser(player.uniqueId)
                if (job == null || user == null) {
                    player.sendMessage(Message.INTERNAL_ERROR.getString().get())
                    return
                }
                user.job = job
                user.jobChangeDate = LocalDateTime.now()
                main.economy.withdrawPlayer(player, 100000.0)
                player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString()
                    .replace("%job", job.friendlyName).get())
                player.sendMessage(Message.JOB_CHANGE_PAY.getString().get())
                Util.openGUI(player, GUIView.JOBS)
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
            }
        }
    }

}