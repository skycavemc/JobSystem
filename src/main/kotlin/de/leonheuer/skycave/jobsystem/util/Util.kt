package de.leonheuer.skycave.jobsystem.util

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.Job
import de.leonheuer.skycave.jobsystem.enums.Message
import de.leonheuer.skycave.jobsystem.enums.RequirementResult
import de.leonheuer.skycave.jobsystem.enums.SellItem
import de.leonheuer.skycave.jobsystem.model.CustomItem
import de.leonheuer.skycave.jobsystem.model.User
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.java.JavaPlugin
import java.time.format.DateTimeFormatter

object Util {

    private val main = JavaPlugin.getPlugin(JobSystem::class.java)

    @Suppress("Deprecation")
    fun openShop(player: Player) {
        val user = main.dataManager.getUser(player.uniqueId)
        if (user == null) {
            player.sendMessage(Message.JOB_UNSET.getString().get())
            return
        }

        player.playSound(player.location, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f, 1.0f)

        val inv = Bukkit.createInventory(player, 45, Message.JOB_SELL_TITLE.getString().get(prefix = false))
        placeholders(inv, 1)
        placeholders(inv, 5)

        var slot = 9
        SellItem.values().filter { it.job == user.job }.forEach {
            inv.setItem(slot, CustomItem(it.material, it.amount).setName("§6${it.friendlyName}")
                .setLore("§7Anzahl: §e${it.amount}", "§7Preis: §e${it.price}$")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .itemStack)
            slot++
        }

        player.openInventory(inv)
    }

    @Suppress("Deprecation")
    fun openSelector(player: Player) {
        val inv = Bukkit.createInventory(player, 45, Message.JOB_SELECTOR_TITLE.getString().get(prefix = false))
        placeholders(inv, 1)
        placeholders(inv, 5)

        val user = main.dataManager.getUser(player.uniqueId)
        if (user == null) {
            inv.setItem(2, CustomItem(Material.CLOCK, 1)
                .setName("§6Letzter Berufswechsel:")
                .setLore("§cnoch nie")
                .itemStack)
            inv.setItem(4, CustomItem(Material.BARRIER, 1)
                .setName("§6Dein Beruf:")
                .setLore("§cDu hast keinen Beruf")
                .itemStack)
            inv.setItem(6, CustomItem(Material.PAPER, 1)
                .setName("§6Kostenlose Berufswechsel:")
                .setLore("§cDer erste Beruf ist immer kostenlos.")
                .itemStack)
        } else {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yy hh:mm")
            inv.setItem(2, CustomItem(Material.CLOCK, 1)
                .setName("§6Letzter Berufswechsel:")
                .setLore("§b${formatter.format(user.jobChangeDate)}",
                    "", "§7Du kannst alle 48 Stunden", "§7deinen Beruf wechseln")
                .itemStack)
            inv.setItem(4, CustomItem(user.job.icon, 1)
                .setName("§6Dein Beruf:")
                .setLore("§b${user.job.friendlyName}", "", "§7Dein Beruf bestimmt, welche Items",
                    "§7du am Spawn verkaufen kannst.")
                .itemStack)
            inv.setItem(6, CustomItem(Material.PAPER, 1)
                .setName("§6Kostenlose Berufswechsel:")
                .setLore("§7Du kannst noch §b${user.freeJobChanges} mal", "§7kostenlos deinen Beruf wechseln.",
                    "", "§7Hast du keinen kostenlosen Wechsel", "§7mehr übrig, musst du §6100.000$ §7zahlen.")
                .itemStack)
        }

        var slot = 19
        Job.values().forEach {
            inv.setItem(slot, CustomItem(it.icon, 1)
                .setName("§b${it.friendlyName}").setLore("§7Klicke für Berufswechsel")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES).itemStack)
            slot++
        }

        player.openInventory(inv)
    }

    private fun placeholders(inv: Inventory, line: Int) {
        for (i in (line-1) * 9 until line * 9) {
            inv.setItem(i, CustomItem(Material.BLACK_STAINED_GLASS_PANE, 1).setName("§0").itemStack)
        }
    }

    fun getRequirementResult(player: Player, user: User): RequirementResult {
        if (user.freeJobChanges > 0) {
            user.freeJobChanges.dec()
            return RequirementResult.USE_FREE
        }
        if (main.economy.getBalance(player) >= 100000.0) {
            main.economy.withdrawPlayer(player, 100000.0)
            return RequirementResult.PAY
        }
        return RequirementResult.NO_MONEY
    }

}