package de.leonheuer.skycave.jobsystem.listener

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.*
import de.leonheuer.skycave.jobsystem.util.Util
import org.bukkit.Material
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
        val view = GUIView.fromString(player.openInventory.title) ?: return

        event.isCancelled = true

        // control items
        when (item.itemMeta!!.displayName) {
            "§6Berufe" -> {
                CustomSound.CLICK.playTo(player)
                Util.openGUI(player, GUIView.JOBS)
                return
            }
            "§6Allgemeiner Ankauf" -> {
                CustomSound.CLICK.playTo(player)
                Util.openGUI(player, GUIView.SELL)
                return
            }
            "§6Persönlicher Ankauf" -> {
                CustomSound.CLICK.playTo(player)
                Util.openGUI(player, GUIView.SELL_PERSONAL)
                return
            }
            "§bUmrechnung" -> {
                CustomSound.CLICK.playTo(player)
                Util.setNextCalcAmount(player.uniqueId)
                Util.openGUI(player, view)
                return
            }
            "§bVerkauf" -> {
                CustomSound.CLICK.playTo(player)
                Util.setNextSellAmount(player.uniqueId)
                Util.openGUI(player, view)
                return
            }
        }

        // inner items
        when (player.openInventory.title) {
            GUIView.JOBS.getTitle() -> {
                val job = Job.fromItemStack(item) ?: return
                val user = main.dataManager.getRegisteredUser(player)
                val result = Util.getRequirementResult(player, user)

                if (result == RequirementResult.FIRST) {
                    Util.openConfirmGUI(player, job, result)
                    return
                }

                if (job == user.job) {
                    CustomSound.ERROR.playTo(player)
                    player.sendMessage(Message.JOB_CHANGE_ALREADY.getString().replaceAll("%job", job.friendlyName).get())
                    return
                }

                if (Duration.between(user.jobChangeDate, LocalDateTime.now()).toHours() < 48) {
                    CustomSound.ERROR.playTo(player)
                    player.sendMessage(Message.JOB_CHANGE_WAIT.getString().get())
                    return
                }

                when (result) {
                    RequirementResult.NO_MONEY -> {
                        CustomSound.ERROR.playTo(player)
                        player.sendMessage(Message.JOB_CHANGE_NO_MONEY.getString().get())
                    }
                    else -> {
                        Util.openConfirmGUI(player, job, result)
                    }
                }
            }
            GUIView.SELL.getTitle() -> {
                val sellItem = GlobalItem.fromItemStack(item) ?: return
                Util.sellItem(player, sellItem)
            }
            GUIView.SELL_PERSONAL.getTitle() -> {
                val user = main.dataManager.getRegisteredUser(player)
                val job = user.job
                if (job == null) {
                    player.sendMessage(Message.SELL_JOB_REQUIRED.getString().get())
                    return
                }
                val sellItem = JobSpecificItem.fromItemStack(item, job) ?: return
                Util.sellItem(player, sellItem)
            }
            GUIView.CONFIRM.getTitle() -> {
                if (item.type == Material.RED_CONCRETE) {
                    CustomSound.ERROR.playTo(player)
                    player.sendMessage(Message.JOB_CHANGE_ABORT.getString().get())
                }
                if (item.type == Material.LIME_CONCRETE) {
                    val job = Util.extractJobFromItemMeta(item)
                    val result = Util.extractResultFromItemMeta(item)
                    if (job == null || result == null) {
                        player.sendMessage(Message.INTERNAL_ERROR.getString().get())
                        return
                    }
                    val user = main.dataManager.getRegisteredUser(player)

                    when (result) {
                        RequirementResult.PAY -> {
                            user.job = job
                            user.jobChangeDate = LocalDateTime.now()
                            main.economy.withdrawPlayer(player, 100000.0)
                            CustomSound.SUCCESS.playTo(player)
                            player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString()
                                .replace("%job", job.friendlyName).get())
                            player.sendMessage(Message.JOB_CHANGE_PAY.getString().get())
                        }
                        RequirementResult.FIRST -> {
                            user.job = job
                            user.jobChangeDate = LocalDateTime.now()
                            CustomSound.SUCCESS.playTo(player)
                            player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString().replace("%job", job.friendlyName).get())
                            Util.openGUI(player, GUIView.JOBS)
                        }
                        RequirementResult.USE_FREE -> {
                            user.job = job
                            user.jobChangeDate = LocalDateTime.now()
                            user.freeJobChanges -= 1
                            CustomSound.SUCCESS.playTo(player)
                            player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString()
                                .replace("%job", job.friendlyName).get())
                            player.sendMessage(Message.JOB_CHANGE_USE_FREE.getString()
                                .replace("%amount", user.freeJobChanges.toString()).get())
                            Util.openGUI(player, GUIView.JOBS)
                        }
                        else -> {
                            // ignored
                        }
                    }
                }
                Util.openGUI(player, GUIView.JOBS)
            }
        }
    }

}