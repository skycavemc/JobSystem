package de.leonheuer.skycave.jobsystem.util

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.*
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
    fun openGUI(player: Player, view: GUIView) {
        val inv = Bukkit.createInventory(player, 45, view.getTitle())
        placeholdersByPattern(inv, 0, "bnnnbnnnb")

        val user = main.dataManager.getUser(player.uniqueId)
        if (user == null) {
            inv.setItem(1, CustomItem(Material.CLOCK, 1)
                .setName("§6Letzter Berufswechsel:")
                .setLore("§cnoch nie")
                .itemStack)
            inv.setItem(2, CustomItem(Material.BARRIER, 1)
                .setName("§6Dein Beruf:")
                .setLore("§cDu hast keinen Beruf")
                .itemStack)
            inv.setItem(3, CustomItem(Material.PAPER, 1)
                .setName("§6Kostenlose Berufswechsel:")
                .setLore("§cDer erste Beruf ist immer kostenlos.")
                .itemStack)
            inv.setItem(5, CustomItem(Material.FLETCHING_TABLE, 1)
                .setName("§6Berufe")
                .setLore("§7Öffnet die Auswahl der Berufe")
                .itemStack)
            inv.setItem(6, CustomItem(Material.IRON_INGOT, 1)
                .setName("§6Allgemeiner Ankauf")
                .setLore("§7Öffnet den Ankauf")
                .itemStack)
            inv.setItem(7, CustomItem(Material.GOLD_INGOT, 1)
                .setName("§6Persönlicher Ankauf")
                .setLore("§7Öffnet einen Ankauf, der je", "§7nach Beruf variiert.")
                .itemStack)
        } else {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yy hh:mm")
            inv.setItem(1, CustomItem(Material.CLOCK, 1)
                .setName("§6Letzter Berufswechsel:")
                .setLore("§b${formatter.format(user.jobChangeDate)}",
                    "", "§7Du kannst alle 48 Stunden", "§7deinen Beruf wechseln")
                .itemStack)
            inv.setItem(2, CustomItem(user.job.icon, 1)
                .setName("§6Dein Beruf:")
                .setLore("§b${user.job.friendlyName}", "", "§7Dein Beruf bestimmt, welche Items",
                    "§7du am Spawn verkaufen kannst.")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .itemStack)
            inv.setItem(3, CustomItem(Material.PAPER, 1)
                .setName("§6Kostenlose Berufswechsel:")
                .setLore("§7Du kannst noch §b${user.freeJobChanges} mal", "§7kostenlos deinen Beruf wechseln.",
                    "", "§7Hast du keinen kostenlosen Wechsel", "§7mehr übrig, musst du §6100.000$ §7zahlen.")
                .itemStack)
            inv.setItem(5, CustomItem(Material.FLETCHING_TABLE, 1)
                .setName("§6Berufe")
                .setLore("§7Öffnet die Auswahl der Berufe")
                .itemStack)
            inv.setItem(6, CustomItem(Material.IRON_INGOT, 1)
                .setName("§6Allgemeiner Ankauf")
                .setLore("§7Öffnet den Ankauf")
                .itemStack)
            inv.setItem(7, CustomItem(Material.GOLD_INGOT, 1)
                .setName("§6Persönlicher Ankauf")
                .setLore("§7Öffnet einen Ankauf, der je", "§7nach Beruf variiert.")
                .itemStack)
        }

        when (view) {
            GUIView.JOBS -> {
                var slot = 19
                Job.values().forEach {
                    if (slot.mod(9) == 0) {
                        slot ++
                    } else if ((slot + 1).mod(9) == 0) {
                        slot += 2
                    }
                    inv.setItem(slot, CustomItem(it.icon, 1)
                        .setName("§b${it.friendlyName}").setLore("§7Klicke für Berufswechsel")
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES).itemStack)
                    slot++
                }
            }
            GUIView.SELL -> TODO("public ankauf")
            GUIView.SELL_PERSONAL -> {
                if (user == null) {
                    player.sendMessage(Message.JOB_UNSET.getString().get())
                    player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 0.7f)
                    return
                }

                var slot = 19
                SellItem.values().filter { it.job == user.job }.forEach {
                    if (slot.mod(9) == 0) {
                        slot ++
                    } else if ((slot + 1).mod(9) == 0) {
                        slot += 2
                    }
                    inv.setItem(slot, CustomItem(it.material, it.amount).setName("§6${it.friendlyName}")
                        .setLore("§7Anzahl: §e${it.amount}", "§7Preis: §e${it.price}$")
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .itemStack)
                    slot++
                }
            }
        }

        player.openInventory(inv)
    }

    private fun placeholdersByPattern(inv: Inventory, startRow: Int, vararg pattern: String) {
        var lineCount = startRow
        for (line in pattern) {
            if (line.length != 9) {
                continue
            }
            val parts = line.split("")
            var slot = lineCount
            for (identifier in parts) {
                if (identifier.length != 1) {
                    continue
                }
                val mat = patternToMaterial(identifier)
                println(mat)
                if (mat != null) {
                    inv.setItem(slot, CustomItem(mat, 1).setName("§0").itemStack)
                }
                slot++
            }
            lineCount += 9
        }
    }

    private fun patternToMaterial(identifier: String): Material? {
        return when (identifier) {
            "n" -> null
            "w" -> Material.WHITE_STAINED_GLASS_PANE
            "b" -> Material.BLACK_STAINED_GLASS_PANE
            else -> null
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